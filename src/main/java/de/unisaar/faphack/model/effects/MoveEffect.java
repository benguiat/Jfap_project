package de.unisaar.faphack.model.effects;

import de.unisaar.faphack.model.Direction;
import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.TraitOwner;
import de.unisaar.faphack.model.map.Tile;

public class MoveEffect implements Effect<Character, Boolean> {

    private Direction dir;

    public MoveEffect(Direction d) {
        dir = d;
        if (java.lang.Math.abs(dir.x) > 1 || java.lang.Math.abs(dir.y) > 1) {
            throw new IllegalArgumentException("Your legs are not long enough to move there!");
        }
    }

    /**
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
