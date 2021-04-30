package bowling.view;

import bowling.exception.BowlingException;

import java.util.Scanner;

public final class InputView {

    private final Scanner scanner;

    public InputView() {
        this(new Scanner(System.in));
    }

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String inputPlayerName() {
        System.out.print("플레이어의 이름을 입력해주세요.(영문 대소문자 3자이내): ");
        return scanner.nextLine();
    }

    public int inputDownPin(int roundNumber) {
        System.out.print(roundNumber + "프레임 투구 : ");
        final String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new BowlingException("유효하지 않은 숫자입니다. 입력한 값: " + input);
        }
    }
}
