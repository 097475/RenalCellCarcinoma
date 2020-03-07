package renalCellCarcinoma;

import java.util.stream.StreamSupport;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

public class DendriticCell extends Cell {

	public DendriticCell(Grid<Cell> space) {
		super(space);
		// TODO Auto-generated constructor stub
	}
	
	@ScheduledMethod(start = 1, interval = 1, pick = 1)
	public void step() {
		Context<Cell> context = ContextUtils.getContext(this);
		long nfat = StreamSupport.stream(context.getObjects(Adipocyte.class).spliterator(), false).count();
		long ndend = StreamSupport.stream(context.getObjects(DendriticCell.class).spliterator(), false).count();
		double rand = RandomHelper.nextDouble();
		//System.out.println(rand + " " + (double) (100-(nfat - fatCutoff))/100);
		if((double) ((nfat - fatCutoff) * 0.05) > ndend) {
			DendriticCell d = new DendriticCell(space);
			context.add(d);
			int x = RandomHelper.nextIntFromTo(0, 49);
			int y = RandomHelper.nextIntFromTo(0, 49);
			space.moveTo(d, x, y);
		}
	}
}
