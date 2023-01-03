package teamProject;

import java.io.*;
import java.util.*;
//결과 로직상 1 == no solution의 value, 0 == 사용된 varaiable
public class ReadResult{
	static SparseMatrix mat = new SparseMatrix(80000,3002); // 행의 첫 두 열에는 순서대로 solution value, time 그 뒤에 solution variable (0,0) = 첫 solution value

	public static void setSvalue(int row, int column, Double dValue){
		// 1인 경우는 결과 txt파일에서 사용되지 않은 변수를 의미 그럼으로 저장할 필요없음, 1이 아닌경우만 저장
		if(dValue!=1) {
			//3001번째부분인 결과값을 반환 0에서 시작
			if(column==3000){
				mat.setElement(row, 0, dValue);
			}
			else if(column==3001){
				mat.setElement(row, 1, dValue);
			}
			else{
				mat.setElement(row, column+2, dValue);
			}
		}
	} //end of setSvalue method

	public static SparseMatrix GetMatrix() {
		// Get file path using relative path
		String filePath = "./data/resultsample.txt";
		File file = new File(filePath);
		Double dValue = 0.00;
		int rowCount = 0; //세로줄 (0,2)에서 0
		int columnCount = 0; //가로줄 (0,2)에서 2
		//read file using stream
		try(Scanner sc = new Scanner(new FileInputStream(file))){

			while(sc.hasNext()){
//				//read 1line by 1line
				while(sc.hasNext()&&columnCount<3002){
					String sValue = sc.next();
					try{
						//sc.next는 기본적으로 string을 반환하기에 다시 double 타입으로 변환
						dValue = Double.parseDouble(sValue);
						setSvalue(rowCount, columnCount, dValue);
					}//end of try
					catch (Exception e){
						//에러시 수행
//						e.printStackTrace(); //오류 출력
						//System.out.println("에러 있음" + sValue);
						//System.out.println("에러 있음:" + columnCount);
						// 0에 한없이 가까운 변수값은 0으로 반환 -> lp 문제에 사용된 변수라는 것은 표시
						//no Solution인 경우에도 0 반환
						if(sValue.equals("no")){
							dValue = 0.00;
							setSvalue(rowCount, columnCount, dValue);
						}else if(sValue.equals("solution")) {
							columnCount--;
						}
						//format 길이 8 1.00+e04
						//1.14e-04-9.62e-20 인 경우의 error잡기 위한 로직 뒷부분은 음수 값이기에 0으로 처리
						else{
							String sValue1 = sValue.substring(0, 8);
							String sValue2 = sValue.substring(8, 17);
							//System.out.println("에러 있음" + sValue1);
							//System.out.println("에러 있음:" + sValue2);
							Double dValue1 = Double.parseDouble(sValue1);
							Double dValue2 = 0.00;
							setSvalue(rowCount, columnCount, dValue1);
							columnCount ++;
							setSvalue(rowCount, columnCount, dValue2);
						} //end of else statement
					}//end of catch statement
					columnCount ++; //3001까지 순회함
				} //end of while
				columnCount = 0;
				rowCount ++;
			} //end of while
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.print("값은 " + mat.getElement(36, 1));
		return mat;
	}
	//end of GetMatrix
}
