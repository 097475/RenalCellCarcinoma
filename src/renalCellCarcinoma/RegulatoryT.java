package renalCellCarcinoma;


import java.util.List;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

public class RegulatoryT extends Cell {

	public RegulatoryT(Grid<Cell> space) {
		super(space);
		// TODO Auto-generated constructor stub
	}
	
	@ScheduledMethod(start = 1, interval = 1)
	public void step() {
		GridPoint pt = space.getLocation(this);

		// use the GridCellNgh class to create GridCells for
		// the surrounding neighborhood.
		GridCellNgh<RegulatoryT> nghCreator = new GridCellNgh<RegulatoryT>(space, pt,
				RegulatoryT.class, 50, 50);
		
		List<GridCell<RegulatoryT>> gridCells = nghCreator.getNeighborhood(false);
		GridPoint target = null;
		int maxCount = -1;
		for (GridCell<RegulatoryT> cell : gridCells) {
			if (cell.size() > maxCount) {
				target = cell.getPoint();
				maxCount = cell.size();
			}
		}
		int xdistance = target.getX() - pt.getX();
		int ydistance = target.getY() - pt.getY();
		if(Math.abs(xdistance) <= 1 && Math.abs(ydistance) <= 1) {
			return;
		}
		int movx = xdistance > 0 ? 1 : xdistance < 0 ? -1 : 0;
		int movy = ydistance > 0 ? 1 : ydistance < 0 ? -1 : 0;
		space.moveTo(this, pt.getX() + movx, pt.getY() + movy);
	}
	
	

}
