package main;

import java.util.Random;

import entity.Entity;

public final class Utility {
	
	static Random random = new Random(System.currentTimeMillis());
	
	private Utility() {}
	
	
	public static int generateRandom(int rangeMin, int rangeMax) {
		return rangeMin + random.nextInt(rangeMax - rangeMin + 1);
	}
	
	// returns an array of Strings that contains direction, speedX, and speedY
	public static String[] homeTowardDest(int entityX, int entityY, int destX, int destY, double speed) {
		
		int distanceX = destX - entityX;
		int distanceY = destY - entityY;
		double speedX;
		double speedY;
		String direction;
		
		if (distanceX != 0 || distanceY != 0) {
			// calculate slope and check for dividing by zero
			double slope;
			try {
				slope = (double)distanceY / (double)distanceX;
			} catch(ArithmeticException e) {
				slope = Double.POSITIVE_INFINITY;
			}
			
			// uses the X focused equation if the distance is more horizontal than vertical, and the Y focused equation if the distance is more vertical than horizontal
			if(Math.abs(distanceY) > Math.abs(distanceX)) {
				speedX = (Math.round(speed / Math.sqrt(1 + slope * slope)) * Integer.signum(distanceX));
				speedY = speed * Integer.signum(distanceY);
			} else {
				speedY = (Math.round(Math.abs(slope) * speed / Math.sqrt(1 + slope * slope)) * Integer.signum(distanceY));
				speedX = speed * Integer.signum(distanceX);
			}
			// if both speeds are maxed, make both speeds one less
			if(Math.abs(speedX) == speed && Math.abs(speedY) == speed && speed != 1) {
				speedX = speedX - Integer.signum(distanceX);
				speedY = speedY - Integer.signum(distanceY);
			}
	        // if the slope is infinity, speedY is maxed (prevents entity from freezing)
	        if(slope == Double.POSITIVE_INFINITY || slope == Double.NEGATIVE_INFINITY) {
	        	speedY = speed * Integer.signum(distanceY);
	        }
			// if the entity is right next to the destination it clips to the destination
			if(Math.abs(speedX) > Math.abs(distanceX)) {
				speedX = distanceX;
			}
			if(Math.abs(speedY) > Math.abs(distanceY)) {
				speedY = distanceY;
			}
			
			if(Math.abs(slope) < 1.5) {
				if(Integer.signum(distanceX) == 1) {
					direction = "right";
				} else {
					direction = "left";
				}
			} else {
				if(Integer.signum(distanceY) == 1) {
					direction = "down";
				} else {
					direction = "up";
				}
			} 
			
		} else {
			speedX = 0;
			speedY = 0;
			direction = "idle";
		}
		return new String[] {direction, String.valueOf(speedX), String.valueOf(speedY)};
	}
	
	public static int[] extrapolatePointByDistance(int x1, int y1, int x2, int y2, int distance) {
        // Calculate the direction vector
        int dx = x2 - x1;
        int dy = y2 - y1;
        
        if(dx == 0) {
        	dx = 1;
        }
        if(dy == 0) {
        	dy = 1;
        }

        // Calculate the length of the direction vector
        double length = Math.sqrt(dx * dx + dy * dy);

        // Normalize the direction vector to get the unit vector
        double unitDx = dx / length;
        double unitDy = dy / length;

        // Calculate the new point at the given distance from (x2, y2)
        int x = (int) Math.round(x2 + distance * unitDx);
        int y = (int) Math.round(y2 + distance * unitDy);

        return new int[]{x, y};
    }
	
	public static double calculateKnockbackSpeed(int x1, int y1, int x2, int y2, int curFrame, int endStamp) {
		
		int dx = x2 - x1;
        int dy = y2 - y1;
        
        double length = Math.sqrt(dx * dx + dy * dy);
        
        return length / (endStamp - curFrame);
	}
	
	public static int[] findSineDest(int x1, int y1, int x2, int y2, int entityX, int entityY, int destX, int destY, int spawnX, int spawnY, double offset, int frameCount) {
		
		int distanceX = x2 - x1;
        int distanceY = y2 - y1;
		
		double distanceEntityLine = Math.sqrt(Math.pow(destX - entityX, 2) + Math.pow(destY - entityY, 2));
		double distanceSpawnLine = Math.sqrt(Math.pow(destX - spawnX, 2) + Math.pow(destY - spawnY, 2));
		
		double taper = distanceEntityLine / distanceSpawnLine;
		
		//System.out.println(taper);
		//System.out.println(1 - Math.abs(distanceEntityLine / distanceSpawnLine));
		
        double sineLocation = 50 * Math.sin((((double)frameCount * Math.PI / 100) % (2 * Math.PI)) + offset);
        //System.out.println(sineLocation);
       
        int pointX = (int) (destX + distanceX * sineLocation / 100 * Math.pow(taper, 1));
        int pointY = (int) (destY + distanceY * sineLocation / 100 * Math.pow(taper, 1));
        
        int trim = 5; // dont change this number
        
        if(pointX >= destX - trim && pointX <= destX + trim) {
        	pointX = destX;
        }
        if(pointY >= destY - trim && pointY <= destY + trim) {
        	pointY = destY;
        }
		
		
        //System.out.println(pointX + " " + pointY);
        return new int[] {pointX, pointY};
	}
	
	public static int[] calculatePerpendicularPoints(int x1, int y1, int x2, int y2, int length) {
		
        int distanceX = x2 - x1;
        int distanceY = y2 - y1;
        double slopePerpendicular;
        
        if (distanceX == 0) {
            slopePerpendicular = 0;
        } else if (distanceY == 0) {
            slopePerpendicular = Double.POSITIVE_INFINITY;
        } else {
            slopePerpendicular = (double)-distanceX / (double)distanceY;
        }

        double radians = Math.atan(slopePerpendicular);
        double dx = Math.cos(radians) * ((double)length / 2);
        double dy = Math.sin(radians) * ((double)length / 2);
        
        int x3 = (int)(x2 + dx);
        int y3 = (int)(y2 + dy);
        int x4 = (int)(x2 - dx);
        int y4 = (int)(y2 - dy);
        
        return new int[] { x3, y3, x4, y4 };
    }
	
	public static int[] findFleeLocation(Entity entity, Entity mower) {
		
		int destX = 0;
		int destY = 0;
		
		double distanceX = entity.entityX - mower.entityX;
		double distanceY = entity.entityY - mower.entityY;
		
		double slope;
		try {
			slope = distanceY / distanceX;
		} catch(ArithmeticException e) {
			slope = Double.POSITIVE_INFINITY;
		}
		
		if(Math.abs(slope) > 1) {
			if(distanceY > 0) {
				destY = Constants.SCREEN_HEIGHT + 1;
			} else {
				destY = 0 - Constants.TILE_SIZE;
			}
			destX = (int) (((double)(destY - entity.entityY)) / slope + entity.entityX);
		} else {
			if(distanceX > 0) {
				destX = Constants.SCREEN_WIDTH + 1;
			} else {
				destX = 0 - Constants.TILE_SIZE;
			}
			destY = (int) (slope * destX + entity.entityY);
		}
		
		if((destY < Constants.SKY_LEVEL * Constants.TILE_SIZE) && entity.getClass().getSimpleName().equals("Ladybug")) {
			if(slope > 0) {
				destX = 0 - Constants.TILE_SIZE;
			} else {
				destX = Constants.SCREEN_WIDTH + 1;
			}
			destY = Constants.SKY_LEVEL * Constants.TILE_SIZE;
		}
		
		return new int[] {destX, destY};
	}
	
}






