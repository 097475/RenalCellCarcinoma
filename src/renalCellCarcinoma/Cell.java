package renalCellCarcinoma;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import repast.simphony.query.space.grid.GridCell;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.SimUtilities;

public class Cell {
	protected Grid<Cell> space;
	protected final static int fatCutoff = 50;
	
	public Cell(Grid<Cell> space) {
		this.space = space;
	}
	
	public List<GridPoint> getEmptyNgh(GridPoint pt){
		List<GridPoint> l = new ArrayList<>();
		int x = pt.getX();
		int y = pt.getY();
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if(x+i >=0 && x+i < 50 && y+j >= 0 && y+j < 50 && space.getObjectAt(x+i, y+j) == null) {
					l.add(new GridPoint(x+i, y+j));
				}
			}
		}
		SimUtilities.shuffle(l, RandomHelper.getUniform());
		return l;
		
	}

}
