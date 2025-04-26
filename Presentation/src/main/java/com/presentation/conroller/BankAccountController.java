package com.presentation.conroller;



import Model.BankAccount;
import Model.User;
import Services.BankAccountService;

import Services.TransactionService;
import Services.UserService;
import com.presentation.entity.BalanceDTO;
import com.presentation.entity.LoginRequest;
import com.presentation.entity.TransferRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/accounts")
@Tag(name = "Управление банковскими счетами", description = "Операции с банковскими счетами")
public class BankAccountController {

    private BankAccountService bankAccountService;

    private TransactionService transactionService;

    private UserService userService;

    public BankAccountController(BankAccountService bankAccountService,
                                 TransactionService transactionService,
                                 UserService userService) {
        this.bankAccountService = bankAccountService;
        this.transactionService = transactionService;
        this.userService = userService;

    }

    @Operation(summary = "Создание нового счета",
            description = "Создает новый банковский счет для пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Счет успешно создан"),
            @ApiResponse(responseCode = "400", description = "Неверные входные данные"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PostMapping
    public ResponseEntity<?> createBankAccount(@RequestBody LoginRequest login) {
        boolean created = bankAccountService.tryAddBankAccount(login.getLogin());
        if (created) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Счет создан");
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка при создании счета");
        }
    }

    @Operation(summary = "Получение баланса",
            description = "Возвращает текущий баланс счета и транзакции")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Баланс получен",
                    content = @Content(schema = @Schema(implementation = BalanceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Счет не найден")
    })
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<?> getAccountInfo(@PathVariable String accountId) {
        BankAccount account = bankAccountService.findById(accountId);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
                new BalanceDTO(
                        account.getBalance(),
                        account.getTransactions()
                )
        );
    }


    @Operation(summary = "Пополнение счета",
            description = "Пополняет указанный счет на заданную сумму")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Счет успешно пополнен"),
            @ApiResponse(responseCode = "400", description = "Неверная сумма или ошибка операции"),
            @ApiResponse(responseCode = "404", description = "Счет не найден")
    })
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<?> deposit(@PathVariable String accountId, @RequestParam double amount) {
        BankAccount account = bankAccountService.findById(accountId);
        if (account == null) return ResponseEntity.notFound().build();

        boolean success = bankAccountService.tryDeposit(account, amount);
        return success
                ? ResponseEntity.ok("Счет пополнен")
                : ResponseEntity.badRequest().body("Ошибка пополнения");
    }

    @Operation(summary = "Снятие средств",
            description = "Снимает средства с указанного счета")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Средства успешно сняты"),
            @ApiResponse(responseCode = "400", description = "Недостаточно средств или ошибка операции"),
            @ApiResponse(responseCode = "404", description = "Счет не найден")
    })
    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable String accountId, @RequestParam double amount) {
        BankAccount account = bankAccountService.findById(accountId);
        if (account == null) return ResponseEntity.notFound().build();

        boolean success = bankAccountService.tryWithdraw(account, amount);
        return success
                ? ResponseEntity.ok("Снятие успешно")
                : ResponseEntity.badRequest().body("Недостаточно средств");
    }

    @Operation(summary = "Перевод между счетами",
            description = "Выполняет перевод средств между счетами")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Перевод выполнен успешно"),
            @ApiResponse(responseCode = "400", description = "Ошибка перевода (недостаточно средств)"),
            @ApiResponse(responseCode = "404", description = "Один из счетов не найден")
    })
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request) {
        BankAccount from = bankAccountService.findById(request.getFromAccountId());
        BankAccount to = bankAccountService.findById(request.getToAccountId());

        if (from == null || to == null) return ResponseEntity.notFound().build();

        User sender = userService.FindUserByLogin(from.getOwnerLogin());
        boolean success = transactionService.tryTransfer(sender, from, to, request.getAmount());

        return success
                ? ResponseEntity.ok("Перевод выполнен")
                : ResponseEntity.badRequest().body("Ошибка перевода (возможно, недостаточно средств)");
    }
}

