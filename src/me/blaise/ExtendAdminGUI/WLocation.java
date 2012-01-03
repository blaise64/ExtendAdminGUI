package me.blaise.ExtendAdminGUI;

public class WLocation {

	public int row;
	public int col;
	public int rowspan;
	public int colspan;

	public WLocation(){
		this.row=1;
		this.col=1;
		this.rowspan=1;
		this.colspan=1;
	}
	
	public WLocation(int row, int col){
		this.row=row;
		this.col=col;
		this.rowspan=1;
		this.colspan=1;
	}
	
	public WLocation(int row, int col, int colspan, int rowspan){
		this.row=row;
		this.col=col;
		this.rowspan=rowspan;
		this.colspan=colspan;
	}
}
