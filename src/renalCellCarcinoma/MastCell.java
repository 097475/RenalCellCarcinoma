package renalCellCarcinoma;

import java.util.List;
import java.util.stream.StreamSupport;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;

public class MastCell extends Cell {

	public MastCell(Grid<Cell> space) {
		super(space);
		// TODO Auto-generated constructor stub
	}
	
	@ScheduledMethod(start = 1, interval = 1)
	public void step() {
		GridPoint pt = space.getLocation(this);

		// use the GridCellNgh class to create GridCells for
		// the surrounding neighborhood.
		GridCellNgh<TumourCell> nghCreator = new GridCellNgh<TumourCell>(space, pt,
				TumourCell.class, 5, 5);
		GridCellNgh<TumourCell> nghCreatorClose = new GridCellNgh<TumourCell>(space, pt,
				TumourCell.class, 1, 1);
		
		List<GridCell<TumourCell>> gridCells = nghCreator.getNeighborhood(false);
		List<GridCell<TumourCell>> gridCellsClose = nghCreatorClose.getNeighborhood(false);
		SimUtilities.shuffle(gridCells, RandomHelper.getUniform());
		boolean isClose = gridCellsClose.stream().filter(x -> x.size() > 0).count() > 0 ? true : false;
		if(!isClose) {
			GridPoint target = null;
			int maxCount = -1;
			for (GridCell<TumourCell> cell : gridCells) {
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
		
		Context<Cell> context = ContextUtils.getContext(this);
		long nfat = StreamSupport.stream(context.getObjects(Adipocyte.class).spliterator(), false).count();
		long nmast = StreamSupport.stream(context.getObjects(MastCell.class).spliterator(), false).count();
		//System.out.println(rand + " " + (double) (100-(nfat - fatCutoff))/100);
		if((double) ((nfat - fatCutoff) * 0.1) > nmast) {
			MastCell m = new MastCell(space);
			context.add(m);
			int x = RandomHelper.nextIntFromTo(0, 49);
			int y = RandomHelper.nextIntFromTo(0, 49);
			space.moveTo(m, x, y);
		}

	}

}
