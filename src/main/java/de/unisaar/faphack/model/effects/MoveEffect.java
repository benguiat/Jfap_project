//Commented!

package de.unisaar.faphack.model.effects;

import de.unisaar.faphack.model.Direction;
import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.TraitOwner;
import de.unisaar.faphack.model.map.Tile;

public class MoveEffect implements Effect<Character, Boolean> {

    private Direction dir;

     /**
     * move character into the given direction only if no more than one tile. 
     * Prevent character from moving more than one tile in any direction.
     *
     * @param d direction to move
     * @return error if assertion fails
     */
    public MoveEffect(Direction d) {
        dir = d;
        if (java.lang.Math.abs(dir.x) > 1 || java.lang.Math.abs(dir.y) > 1) {
            throw new IllegalArgumentException("Your legs are not long enough to move there!");
        }
    }

    /**
     * tries to move the character into the given direction. If the character's
     * power == 0 only moves with direction (0,0) are possible, i.e. the
     * character is resting and its power increases by 5
     *
     * @param c the character to move
     * @return true if successful, false otherwise
     */
    public Boolean apply(Character c) {
        Tile CurrentTile = c.getTile();
        Tile TargetTile = CurrentTile.getNextTile(dir);

        if (TargetTile.getTrait() == TraitOwner.WALL || TargetTile.getTrait() == TraitOwner.DOOR) {
            return false;
        } else {
            if (dir.x > 0 && dir.y > 0) {
                return false;
            } else {
                c.move(TargetTile);
                return true;
            }
        }
    }
}
