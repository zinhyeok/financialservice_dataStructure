package teamProject;

import java.util.Scanner;


public class InvestService {

	static SparseMatrix mat = ReadResult.GetMatrix();// InvestService의 matrix를 ReadResult로 저장한다.
	//public static이라 import나 객체 생성 없이 바로 사용 가능.

	//객체를 생성하면서 바로 서비스 시작
	public InvestService() {
		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.println();
			System.out.println("무엇에 대해 알고 싶습니까?");
			System.out.println("(1)특정 문제에 대해서 (2)특정 변수에 대해서 (0)서비스 종료");
			try {
				int menu = sc.nextInt();
				if (menu == 0) {
					break;
				}
				int x;
				int var;
				switch (menu) {
					case 1 -> {
						System.out.println("몇 번 문제(1~80000)에 대해서 알고 싶습니까? 목적식 값에 대해 알고 싶으시면 0을 입력하세요.");
						var = sc.nextInt();
						if (var == 0) {
							System.out.println("목적식 값의 무엇에 대해 알고 싶습니까?");
						} else {
							System.out.println(var + "번 문제의 무엇에 대해 알고 싶습니까?");
						}
						System.out.println("(1)문제의 시간 (2)문제의 풀렸는지 유무 (3)문제의 목적식값들 (4)문제의 최적해 값들 (5)전체 문제의 평균 걸린 시간");
						x = sc.nextInt();
						ProblemService(x, var);
					}
					case 2 -> {
						System.out.println("몇 번 변수(1~3000)에 대해서 알고 싶습니까? 목적식 값에 대해 알고 싶으시면 0을 입력하세요.");
						var = sc.nextInt();
						if (var == 0) {
							System.out.println("목적값이 특정 값보다 작은지, 큰지를 알려주는 서비스를 제공하고 있습니다.");
							inequality(0);
						} else {
							System.out.println(var + "번 변수 의 무엇에 대해 알고 싶습니까?");
							System.out.println("(1)풀린 문제들 번호 (2)풀린 문제들의 목적식 값들 (3)풀린 문제들 중 이 변수의 최적값 (4)특정 조건");
							x = sc.nextInt();
							VariableService(x, var);
						}
					}
					default -> System.out.println("다시 입력하세요");
				}
			}
			catch (Exception e){
				break;
			}
			sc.close();
		}//end of while

	} //End InvestService

	/*
	 * 고객이 특정 문제에 대해 물어볼 때 실행되는 메소드
	 */
	public static void ProblemService(int x, int var) {
		var-=1; //행렬은 첫 칸이 0이기 때문에 조정해줘야함
		switch (x) {
			case 1 -> System.out.println(mat.getElement(var, 1));// 걸린 시간 반환
			case 2 -> System.out.println(mat.getElement(var, 0) == 0 ? "풀리지 않았습니다" : "풀렸습니다");// 풀렸는지 유무 반환
			case 3 -> System.out.println(mat.getElement(var, 0));// 문제의 목적식값 반환
			case 4 -> {
				int y = 2;// 2부터 3001까지 출력되게
				while (y <= 3001) {
					if (mat.getElement(var, y) >= 0) {
						System.out.println("x" + (y - 1) + "변수의 최적해 : " + mat.getElement(var, y));
					}
					y++;
				}
				System.out.println("나머지 변수들은 사용되지 않았습니다.");
			}
			// 문제의 최적해 값들
			case 5 -> {
				int i = 0;
				double sum = 0;
				while (i < 100)//resultsample로 돌릴 때에는 100을 사용
				{
					sum = sum + mat.getElement(i, 1);
					i++;
				}
				System.out.println(sum / 100);// 평균 걸린 시간,
			}
			default -> System.out.println("다시입력하세요");
		}
	}//end ProblemService

	/*
	 * 고객이 목적값을 포함한 특정 변수에 대해 물어볼 때 실행되는 메소드
	 */
	public static void VariableService(int x, int var) {
		switch (x) {
			case 1 -> {
				String result = "";
				for (int i = 0; i < mat.getNumberOfRows()-1; i++) {
					if (mat.getElement(i, var + 1) >= 0) {
						if (mat.getElement(i, 0) != 0) {
							result += (i + 1) + "\n";
						}
					}
				}
				System.out.println("이 변수가 사용된 문제들 중 ");
				System.out.println(result + " 번 째 문제들이 풀렸습니다.");
			}
			// 변수가 사용된 문제들 중 풀린 문제들 반환
			case 2 -> {
				String result1 = "";
				double max = 0.0;
				double avg = 0.0;
				int count = 0;
				for (int i = 0; i < mat.getNumberOfRows()-1; i++) {
					if (mat.getElement(i, var + 1) >= 0) {
						if (mat.getElement(i, 0) != 0) {
							result1 += (i + 1) + "번째 문제 최적값 : " + mat.getElement(i, 0) + "\n";
							if (mat.getElement(i, 0) > max) {
								max = mat.getElement(i, 0);
							}
							avg += mat.getElement(i, 0);
							count++;
						}
					}
				}
				System.out.println("풀린 문제들의 최적값들은 다음과 같습니다.");
				System.out.println(result1);
				System.out.println("최적값 평균 : " + (avg / count) + " 최적값 최대치 : " + max);
			}
			// 풀린 문제들의 목적식 값들
			case 3 -> {
				String result2 = "";
				for (int i = 0; i < mat.getNumberOfRows()-1; i++) {
					if (mat.getElement(i, var + 1) >= 0) {
						if (mat.getElement(i, 0) != 0) {
							result2 += (i + 1) + "번째 문제에서 이 변수의 최적해 : " + mat.getElement(i, var + 1) + "\n";
						}
					}
				}
				System.out.println("풀린 문제들 중 이 변수의 최적해는 다음과 같습니다.");
				System.out.println(result2);
			}
			// 풀린 문제들 중 이 변수의 최적값
			case 4 -> {
				System.out.println("문제에서 이 변수가 특정 값보다 작은지, 큰지를 알려주는 서비스를 제공하고 있습니다.");
				inequality(var);
			}
			default -> System.out.println("다시 입력하세요");
		}
	}//end varianceService

	/*
	 * 특정 값보다 크거나 작은 값들을 모두 반환하는 메소드
	 */
	private static void inequality(int var) {
		Scanner sc = new Scanner(System.in);
		if(var==0) {
			var-=1; //뒤에 var + 1 작업을 해주기 때문에 목적값을 구하려면 var를 -1로 설정해주어야함.
		}

		System.out.println("특정 값을 입력하세요.");
		double point = sc.nextDouble();
		String sign;
		String result3 = "";
		while (true) {
			System.out.println("이 값 이상인 값들을 원하면 >, 이하인 값들을 원하면 <를 입력하세요");
			sign = sc.next();
			if (sign.equals("<")) {
				for (int i = 0; i < mat.getNumberOfRows()-1; i++) {
					if (mat.getElement(i, var + 1) <= point) {
						if (mat.getElement(i, 0) != 0) {
							result3 += (i+1) + "번째 문제에서 조건에 맞는 이 변수의 최적해 : " + mat.getElement(i, var + 1) + "\n";
						}
					}
				}
				break;
			} else if (sign.equals(">")) {
				for (int i = 0; i < mat.getNumberOfRows()-1; i++) {
					if (mat.getElement(i, var + 1) >= point) {
						if (mat.getElement(i, 0) != 0) {
							result3 += (i+1) + "번째 문제에서 조건에 맞는 이 변수의 최적해 : " + mat.getElement(i, var + 1) + "\n";
						}
					}
				}
				break;
			} else {
				System.out.println("다시 입력하세요.");
			}
		}
		System.out.println("조건에 맞는 변수의 최적해들은 다음과 같습니다.");
		System.out.println(result3);
		sc.close();
	}// end inequality

	public static void main(String[] args) {
		InvestService is = new InvestService(); //생성자만 생성하면 서비스가 시작된다.

	}
}
