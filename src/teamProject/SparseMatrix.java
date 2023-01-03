package teamProject;

import java.util.HashMap;

class MyIndex {

    private int x=0; // nr of rows
    private int y=0; // nr of columns
    private int hashvalue = 0;

    public MyIndex (final int x, final int y)
    {
        this.x=x;
        this.y=y;
        hashvalue=((x+"")+(y+"")).hashCode();
    }

    public boolean equals (final Object obj)
    {
        if (obj instanceof MyIndex)
        {
            MyIndex index = (MyIndex) obj;
            return ((x==index.x) && (y==index.y));
        }
        else
            return false;
    }

    public int hashCode()
    {
        return hashvalue;
    }

}

public class SparseMatrix {

    private int rows;
    private int columns;
    private HashMap<MyIndex,Double> values;
    @SuppressWarnings("unused")
    private boolean validIndex (final int row, final int column)
    {
        return (row>=0 && row<rows && column>=0 && column<columns);
    }


    public SparseMatrix(int rows, int columns)  {
        this.rows=rows;
        this.columns=columns;
        this.values=new HashMap<MyIndex,Double>();
    }


    public int getNumberOfRows() {
        return rows;
    }

    public int getNumberofActiveElements() {
        return values.size();
    }


    public int getNumberOfColumns() {
        return columns;
    }


    public double getElement(int row, int column)  {
        MyIndex index = new MyIndex (row,column);
        if (values.containsKey(index))
            return values.get(index);
        else
            //비어있음 1 반환
            return -1;
    }


    public void setElement(int row, int column, Double value)  {
        MyIndex index = new MyIndex(row,column);
        //1이 들어오는 경우 비어있다는 의미로 remove처리
        if (value==1)
        {
            if (values.containsKey(index))
                values.remove(index);
        }
        else
            values.put(index,value);
    }

}