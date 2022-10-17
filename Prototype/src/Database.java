import javax.swing.table.*;
import java.util.*;

class Database extends AbstractTableModel {
    private List<TableHeader> headers;
    private List<List<TableData>> data;
    
    public Database() {
        headers = new ArrayList<TableHeader>();
        data = new ArrayList<List<TableData>>();
    }
    public void addRow() {
        List<TableData> row = new ArrayList<TableData>();
        for(TableHeader col:headers)
            row.add(col.create()); // wywołanie metody fabrykującej
        data.add(row);
        fireTableStructureChanged();
    }
    public void addCol(TableHeader type) {
        headers.add(type);
        for(List<TableData> row:data)
            row.add(type.create()); // wywołanie metody fabrykującej
        fireTableStructureChanged();
    }

    public int getRowCount() { return data.size(); }
    public int getColumnCount() { return headers.size(); }
    public String getColumnName(int column) {
        return headers.get(column).toString();
    }
    public Object getValueAt(int row, int column) {
        return data.get(row).get(column);
    }
}

abstract class TableData implements Cloneable
{
    protected Random rnd;
    @Override
    public TableData clone() {

		TableData obj = null;

		try {
			obj = (TableData) super.clone();
			obj.doRand();
		} catch (CloneNotSupportedException ex) {
		}
		return obj;
	}
	
	protected abstract void doRand();
}

class TableDataInt extends TableData
{
    private int data;
    public TableDataInt() { doRand(); }
    public String toString() { return Integer.toString(data); }
    @Override
    protected void doRand()
    {
        this.rnd=new Random();
        this.data=this.rnd.nextInt(100);
    };
}
class TableDataDouble extends TableData
{
    private double data;
    public TableDataDouble(){doRand();}
    public String toString(){return Double.toString(data);}
    @Override
    protected void doRand()
    {
        this.rnd=new Random();
        this.data=this.rnd.nextDouble();
    };
}
class TableDataChar extends TableData
{
    private char data;
    public TableDataChar(){doRand();}
    public String toString(){return String.valueOf(data);}
    @Override
    protected void doRand()
    {
        this.rnd=new Random();
        this.data=(char)this.rnd.nextInt(100);
    };
}
class TableDataBoolean extends TableData
{
    private boolean data;
    public TableDataBoolean(){data=false;}
    public String toString(){return Boolean.toString(data);}
    @Override
    protected void doRand()
    {
        this.data=false;
    };
}
abstract class TableHeader {

	protected TableData data;

	public abstract TableData create();
}

class TableHeaderInt extends TableHeader {

	public TableHeaderInt() {
		this.data = new TableDataInt();
	}

	public TableDataInt create() {
		return (TableDataInt) this.data.clone();
	}

	public String toString() {
		return "INT";
	}
}

class TableHeaderDouble extends TableHeader {

	public TableHeaderDouble() {
		this.data = new TableDataDouble();
	}

	public TableDataDouble create() {
		return (TableDataDouble) this.data.clone();
	}

	public String toString() {
		return "DOUBLE";
	}
}

class TableHeaderChar extends TableHeader {

	public TableHeaderChar() {
		this.data = new TableDataChar();
	}

	public TableDataChar create() {
		return (TableDataChar) this.data.clone();
	}

	public String toString() {
		return "CHAR";
	}
}

class TableHeaderBoolean extends TableHeader {

	public TableHeaderBoolean() {
		this.data = new TableDataBoolean();
	}

	public TableDataBoolean create() {
		return (TableDataBoolean) this.data.clone();
	}

	public String toString() {
		return "BOOLEAN";
	}
}