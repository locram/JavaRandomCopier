import java.util.Random;

public class RandomCopierTest {
	public static void main(String... args) {
		System.out.println("Create new Random instance with unknown seed");
		Random random = new Random();
		System.out.println(" ==> random");
		System.out.println("");


		System.out.println("Generate two random integers with random.nextInt()");
		int i1 = random.nextInt();
		int i2 = random.nextInt();
		System.out.println(" ==> i1 = " + i1);
		System.out.println(" ==> i2 = " + i2);
		System.out.println("");


		System.out.println("Use the RandomCopier to generate a new Random "
				+ "instance with the same internal state");
		Random randomCopy = RandomCopier.copy(i1, i2);
		System.out.println(" ==> randomCopy");
		System.out.println("");


		System.out.println("Test whether they output the same numbers");
		for (int i = 0; i < 5; i++) {
			System.out.println("random.nextInt()\t==> " + random.nextInt());
			System.out.println("randomCopy.nextInt()\t==> " + randomCopy.nextInt());
		}
	}
}
