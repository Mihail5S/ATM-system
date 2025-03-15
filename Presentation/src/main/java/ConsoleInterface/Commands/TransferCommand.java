package ConsoleInterface.Commands;

import Model.BankAccount;
import Model.User;
import Service.TransactionService;
import repository.BankAccountRepository;
import repository.UserRepository;

import java.util.Scanner;

/**
 * Команда для перевода средств с одного банковского счета на другой.
 * <p>
 * Эта команда позволяет пользователю перевести средства с одного счета на другой, проверяя наличие
 * пользователей, счетов и достаточность средств. Также после успешного перевода выводится информация
 * о новом балансе обоих участников.
 * </p>
 */
public class TransferCommand implements IConsoleCommand {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final TransactionService transactionService;
    private final Scanner scanner;

    /**
     * Конструктор класса {@link TransferCommand}.
     *
     * @param bankAccountRepository Репозиторий для работы с банковскими счетами.
     * @param userRepository Репозиторий для работы с пользователями.
     * @param scanner Объект для считывания ввода с консоли.
     */
    public TransferCommand(BankAccountRepository bankAccountRepository, UserRepository userRepository, Scanner scanner) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.transactionService = new TransactionService();
        this.scanner = scanner;
    }

    /**
     * Выполняет команду перевода средств между двумя пользователями.
     * Команда выполняет следующие шаги:
     * <ul>
     *   <li>Запрашивает логины и ID счетов отправителя и получателя</li>
     *   <li>Проверяет существование отправителя и получателя, а также их счетов</li>
     *   <li>Запрашивает сумму перевода</li>
     *   <li>Проверяет, что сумма положительная и что на счете отправителя достаточно средств</li>
     *   <li>Если все проверки прошли успешно, выполняет перевод и выводит новые балансы обоих участников</li>
     *   <li>В случае ошибок выводится сообщение о причине неудачи</li>
     * </ul>
     */
    @Override
    public void execute() {
        System.out.print("Введите логин отправителя: ");
        String senderLogin = scanner.nextLine();

        User sender = userRepository.findByLogin(senderLogin);
        if (sender == null) {
            System.out.println("Ошибка! Отправитель не найден.");
            return;
        }

        System.out.print("Введите ID счета отправителя: ");
        String senderAccountId = scanner.nextLine();
        BankAccount senderAccount = bankAccountRepository.findById(senderAccountId);
        if (senderAccount == null || !senderAccount.getOwnerLogin().equals(senderLogin)) {
            System.out.println("Ошибка! Счет отправителя не найден или не принадлежит пользователю.");
            return;
        }

        System.out.print("Введите логин получателя: ");
        String receiverLogin = scanner.nextLine();

        User receiver = userRepository.findByLogin(receiverLogin);
        if (receiver == null) {
            System.out.println("Ошибка! Получатель не найден.");
            return;
        }

        System.out.print("Введите ID счета получателя: ");
        String receiverAccountId = scanner.nextLine();
        BankAccount receiverAccount = bankAccountRepository.findById(receiverAccountId);
        if (receiverAccount == null) {
            System.out.println("Ошибка! Счет получателя не найден.");
            return;
        }

        System.out.print("Введите сумму перевода: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (amount <= 0) {
            System.out.println("Ошибка! Сумма должна быть больше нуля.");
            return;
        }

        if (transactionService.TryTransfer(sender, senderAccount, receiverAccount, amount)) {
            System.out.println("Перевод успешен!");
            System.out.println("Новый баланс отправителя: " + senderAccount.getBalance());
            System.out.println("Новый баланс получателя: " + receiverAccount.getBalance());
        } else {
            System.out.println("Ошибка! Недостаточно средств или перевод невозможен.");
        }
    }
}
