package renalCellCarcinoma;

import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.space.grid.InfiniteBorders;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.StrictBorders;

public class RenalCellCarcinoma implements ContextBuilder<Cell>{
	
	private Context<Cell> context;
	private Grid<Cell> grid;
	// carcinoma, neutrophil, mast, regulatory T, plasmacitoid dendritic, type 1 t helper, dendritic, macrophage
	private double[] c = new double[] {40, 5, 8, 3, 16, 2, 6, 11};
	private double[] h = new double[] {40, 5, 8, 3, 16, 2, 6, 11};
	private final double[][] J = new double[][] {
		{0,1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0},
		{1.0,0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0},
		{-1.0,-1.0,0,1.0,1.0,-1.0,1.0,1.0},
		{-1.0,-1.0,1.0,0,1.0,-1.0,1.0,1.0},
		{-1.0,-1.0,1.0,1.0,0,-1.0,1.0,1.0},
		{1.0,1.0,-1.0,-1.0,-1.0,0,-1.0,-1.0},
		{-1.0,-1.0,1.0,1.0,1.0,-1.0,0,1.0},
		{-1.0,-1.0,1.0,1.0,1.0,-1.0,1.0,0},
		};
	private final double S = 0;

	@Override
	public Context<Cell> build(Context<Cell> context) {
		// TODO Auto-generated method stub
		this.context = context;
		context.setId("RenalCellCarcinoma");
		CellAdder adder = new CellAdder();
		GridBuilderParameters<Cell> param = new GridBuilderParameters<Cell>(new StrictBorders(),
				adder, true, 50, 50);
		/*ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder
				.createContinuousSpaceFactory(null);
		ContinuousSpace<Cell> space = spaceFactory.createContinuousSpace(
				"space", context, new CellAdder(),
				new repast.simphony.space.continuous.StrictBorders(), 50,
				50);*/
		
		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		Grid<Cell> grid = gridFactory.createGrid("space", context, param);	
		
		this.grid = grid;
		Parameters parameters = RunEnvironment.getInstance().getParameters();
		double BMI = parameters.getDouble("BMI");
		//System.out.println(BMI);
		/*for(int i = 0; i < 40; i++) {
			adder.fat(context, space);
		}*/
		/*for(int i = 0; i < 50; i++) {
			context.add(new Blood(space));
		}*/
		
		
		//adder.cancer(context, grid);
		
		
		
		adder.fat(context, grid);
		
		
		adder.bloodVessels(context, grid);
		
		for(int i = 0; i < 7; i++) {
			context.add(new Neutrophil(grid));
		}
		
		for(int i = 0; i < 3; i++) {
			context.add(new DendriticCell(grid));
		}
		
		for(int i = 0; i < 5; i++) {
			context.add(new THelper(grid));
		}
		
		for(int i = 0; i < 2; i++) {
			context.add(new RegulatoryT(grid));
		}
		
		for(int i = 0; i < 6; i++) {
			context.add(new Macrophage(grid));
		}
		
		for(int i = 0; i < 7; i++) {
			context.add(new NaturalKillerCell(grid));
		}
		
		for(int i = 0; i < 2; i++) {
			context.add(new MastCell(grid));
		}
		
		for(int i = 0; i < 3; i++) {
			context.add(new PlasmacitoidDendriticCell(grid));
		}
		
		
		ISchedule schedule = RunEnvironment.getInstance().getCurrentSchedule ();
		ScheduleParameters params = ScheduleParameters.createRepeating(1.0, 1);
		schedule.schedule(params, this, "fatten");
		
		/////////////////////////////////////
		ScheduleParameters params2 = ScheduleParameters.createOneTime(1.0);
		schedule.schedule(params2, this, "h");
		///////////////////////////////////
		
		return context;
	}
	
	public void fatten() {
		Adipocyte f = (Adipocyte) context.getRandomObjects(Adipocyte.class, 1).iterator().next();
		GridPoint pt = grid.getLocation(f);
		List<GridPoint> emptyNgh = f.getEmptyNgh(pt);
		if(emptyNgh.isEmpty()) {
			return;
		}
		GridPoint newpt = emptyNgh.get(0);
		Adipocyte newf = new Adipocyte(grid);
		context.add(newf);
		grid.moveTo(newf, newpt.getX(), newpt.getY());
	}

	public void h() {

		for(int i = 0; i < h.length; i++) {
			c[i] = h[i];  //theta di h
		}
		
		for(int i = 0; i < h.length; i++) {
			h[i] = S;
			for(int k = 0; k < J[i].length; k++) {
				h[i] += J[i][k] * c[k];
			}
		}
		
		for(int i = 0; i < h.length; i++) {
			System.out.println("h_"+i+" = "+h[i]);
		}
	}
}
