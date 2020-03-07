package renalCellCarcinoma;

import java.util.BitSet;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import repast.simphony.context.Context;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousAdder;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridAdder;

public class  CellAdder implements GridAdder<Cell> {
	private final int[][] bloodVessels = new int[][] {
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 64},
        {0, 0, 0, 0, 0, 0, 192},
        {0, 0, 0, 0, 0, 1, 192},
        {0, 0, 0, 0, 0, 3, 192},
        {0, 0, 0, 0, 0, 7, 128},
        {0, 0, 0, 0, 0, 15, 0},
        {0, 0, 0, 0, 0, 30, 0},
        {0, 0, 0, 0, 0, 60, 192},
        {0, 0, 0, 1, 8, 124, 192},
        {0, 0, 0, 1, 12, 253, 192},
        {0, 0, 0, 7, 135, 255, 192},
        {0, 0, 0, 1, 231, 255, 128},
        {0, 0, 0, 0, 119, 255, 0},
        {0, 0, 0, 0, 63, 254, 0},
        {0, 0, 0, 0, 31, 252, 0},
        {0, 0, 0, 0, 63, 248, 0},
        {0, 0, 0, 0, 127, 240, 0},
        {0, 0, 0, 0, 255, 224, 0},
        {0, 0, 0, 1, 255, 192, 0},
        {0, 0, 0, 3, 255, 128, 0},
        {0, 0, 0, 7, 255, 0, 0},
        {0, 0, 0, 15, 254, 1, 192},
        {0, 0, 0, 31, 252, 31, 192},
        {0, 0, 0, 63, 251, 255, 0},
        {0, 0, 0, 127, 255, 255, 0},
        {0, 0, 0, 255, 255, 255, 192},
        };

    private final int[][] cancer = new int[][] {
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {2, 0, 0, 0, 0, 0, 0},
        {16, 64, 0, 0, 0, 0, 0},
        {1, 0, 0, 0, 0, 0, 0},
        {0, 16, 0, 0, 0, 0, 0},
        {8, 64, 0, 0, 0, 0, 0},
        {1, 0, 0, 0, 0, 0, 0},
        {0, 4, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {16, 1, 0, 8, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {32, 0, 64, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 16, 8, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 2, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 32, 0, 128, 0},
        {0, 0, 0, 72, 132, 0, 0},
        {0, 0, 0, 0, 32, 8, 0},
        {0, 0, 0, 128, 0, 64, 0},
        {0, 0, 0, 5, 8, 0, 0},
        {0, 0, 2, 2, 64, 0, 0},
        {0, 0, 0, 16, 4, 32, 0},
        {0, 0, 0, 0, 129, 0, 0},
        {0, 0, 2, 9, 0, 0, 0},
        {0, 0, 0, 36, 0, 0, 0},
        {0, 0, 0, 130, 0, 0, 0},
        {0, 0, 0, 0, 32, 0, 0},
        {0, 0, 0, 128, 0, 0, 0},
        {0, 0, 0, 32, 8, 0, 0},
        {0, 0, 0, 10, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 64},
        {0, 0, 0, 4, 160, 0, 0},
        {0, 0, 0, 9, 0, 0, 0},
        {0, 0, 0, 2, 0, 0, 128},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 5, 0},
        {0, 0, 0, 0, 0, 32, 0},
        {0, 0, 0, 0, 0, 4, 128},
        {0, 0, 0, 0, 0, 32, 0},
        {0, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0, 0, 64},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        };
	
    private final int[][] fat = new int[][]{
        {0, 0, 0, 0, 0, 0, 0},
        {4, 0, 0, 0, 0, 0, 0},
        {16, 16, 0, 0, 0, 0, 0},
        {0, 0, 8, 33, 0, 0, 0},
        {32, 0, 0, 0, 0, 0, 0},
        {0, 0, 2, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 2, 16, 68, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 16, 0, 0, 0, 0, 0},
        {0, 0, 68, 64, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 32, 0, 0, 128, 0, 0},
        {0, 4, 4, 0, 0, 0, 0},
        {129, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {8, 0, 128, 1, 0, 0, 64},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 64, 0},
        {0, 0, 0, 0, 36, 0, 64},
        {0, 0, 16, 2, 0, 16, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 2, 0, 0, 2, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 2, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 4, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 4, 0},
        {0, 0, 8, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 64, 0},
        {0, 0, 8, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 2, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 64, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 32, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 16, 0, 0, 0},
        {0, 0, 0, 4, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        };


	private void add(Grid<Cell> destination, NaturalKillerCell object) {
		int[] location = new int[2];
		Iterator<Cell> iterator = destination.getObjects().iterator();
		Iterable<Cell> iter = () -> iterator;
		Stream<Cell> stream = StreamSupport.stream(iter.spliterator(), false);
		TumourCell ct = (TumourCell) stream.filter(x -> x instanceof TumourCell).findAny().get();
		do {
			int x = destination.getLocation(ct).getX();
			int y = destination.getLocation(ct).getY();
			int rand1 = RandomHelper.nextIntFromTo(-8, 8);
			int rand2 = RandomHelper.nextIntFromTo(-8, 8);
			location[0] = x + rand1 > 49 ? 49 : (x + rand1 < 0 ? 0 : x + rand1) ;
			location[1] = y + rand2 > 49 ? 49 : (y + rand2 < 0 ? 0 : y + rand2);
		}while(!destination.moveTo(object, location));		
	}
	
	public void fat(Context<Cell> context, Grid<Cell> destination) {
		/*Iterator<Cell> it = context.getObjects(TumourCell.class).iterator();
		Iterable<Cell> iter = () -> it;
		Stream<Cell> stream = StreamSupport.stream(iter.spliterator(), false);
		double maxX = stream.map(x -> destination.getLocation(x).getX()).max(Double::compare).get();
		Iterator<Cell> it2 = context.getObjects(TumourCell.class).iterator();
		iter = () -> it2;
		stream = StreamSupport.stream(iter.spliterator(), false);
		double minX = stream.map(x -> destination.getLocation(x).getX()).min(Double::compare).get();
		Iterator<Cell> it3 = context.getObjects(TumourCell.class).iterator();
		iter = () -> it3;
		stream = StreamSupport.stream(iter.spliterator(), false);
		double maxY = stream.map(x -> destination.getLocation(x).getY()).max(Double::compare).get();
		Iterator<Cell> it4 = context.getObjects(TumourCell.class).iterator();
		iter = () -> it4;
		stream = StreamSupport.stream(iter.spliterator(), false);
		double minY = stream.map(x -> destination.getLocation(x).getY()).min(Double::compare).get();
		System.out.println(minX + " " +  maxX);
		Adipocyte f = new Adipocyte(destination);
		context.add(f);
		double[] location = new double[2];
		do {
			Iterator<Cell> iterator = context.getRandomObjects(TumourCell.class, 1).iterator();
			Cell t = iterator.next();
			double x = destination.getLocation(t).getX();
			double y = destination.getLocation(t).getY();
			double rand1 = RandomHelper.nextDoubleFromTo(-25, 25);
			double rand2 = RandomHelper.nextDoubleFromTo(-25, 25);
			location[0] = x + rand1;
			location[1] = y + rand2;
			if(location[0] < 0.0 || location[0] > 49.0 || location[0] > minX || location[0] < maxX ) {
				continue;
			} else if(location[1] < 0.0 || location[1] > 49.0 || location[1] > minY || location[1] < maxY ) {
				continue;
			} else {
				destination.moveTo(f, location);
				break;
			}

		}while(true);
		*/
		int rows = 50;
		int cols = 7;
		int index = rows - 1;
		for(int i = 0 ; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				short hex = 0x80;
				for(int k = 0; k < 8; k++) {
					if((fat[i][j] & hex) == 0) {
						// vuoto
					} else {
						Adipocyte f = new Adipocyte(destination);
						context.add(f);
						destination.moveTo(f, new int[]{8*j + k, index});
					}
					
					hex >>= 1;
				}

			}
			index--;
		}
	}
	
	public void cancer(Context<Cell> context, Grid<Cell> destination) {
		int rows = 50;
		int cols = 7;
		int index = rows - 1;
		for(int i = 0 ; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				short hex = 0x80;
				for(int k = 0; k < 8; k++) {
					if((cancer[i][j] & hex) == 0) {
						// vuoto
					} else {
						TumourCell c = new TumourCell(destination);
						context.add(c);
						destination.moveTo(c, new int[]{8*j + k, index});
					}
					
					hex >>= 1;
				}

			}
			index--;
		}
	}
	
	public void bloodVessels(Context<Cell> context, Grid<Cell> destination) {
		int rows = 50;
		int cols = 7;
		int index = rows - 1;
		for(int i = 0 ; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				short hex = 0x80;
				for(int k = 0; k < 8; k++) {
					if((bloodVessels[i][j] & hex) == 0) {
						// vuoto
					} else {
						Blood b = new Blood(destination);
						context.add(b);
						destination.moveTo(b, new int[]{8*j + k, index});
					}
					
					hex >>= 1;
				}

			}
			index--;
		}	
	}

	@Override
	public void add(Grid<Cell> destination, Cell object) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if(object instanceof NaturalKillerCell) {
			
			add(destination, (NaturalKillerCell) object);
			
		}else if(!(object instanceof Adipocyte) && !(object instanceof Blood) && !(object instanceof TumourCell) ) {
			int[] location = new int[2];
			int x = RandomHelper.nextIntFromTo(0, 49);
			int y = RandomHelper.nextIntFromTo(0, 49);
			location[0] = x;
			location[1] = y;
			destination.moveTo(object, location);
		}
	}
}
