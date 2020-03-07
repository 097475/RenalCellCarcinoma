package renalCellCarcinoma;

import java.util.stream.StreamSupport;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

public class Macrophage extends Cell {

	public Macrophage(Grid<Cell> space) {
		super(space);
		// TODO Auto-generated constructor stub
	}
	
	@ScheduledMethod(start = 1, interval = 1, pick = 1)
	public void step() {
		Context<Cell> context = ContextUtils.getContext(this);
		long nfat = StreamSupport.stream(context.getObjects(Adipocyte.class).spliterator(), false).count();
		long nmacro = StreamSupport.stream(context.getObjects(Macrophage.class).spliterator(), false).count();
		//System.out.println(rand + " " + (double) (100-(nfat - fatCutoff))/100);
		if((double) ((nfat - fatCutoff) * 0.1) > nmacro) {
			Macrophage m = new Macrophage(space);
			context.add(m);
			int x = RandomHelper.nextIntFromTo(0, 49);
			int y = RandomHelper.nextIntFromTo(0, 49);
			space.moveTo(m, x, y);
		}
	}

}
