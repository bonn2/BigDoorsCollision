package bonn2.bigdoorscollision.listeners;

import bonn2.bigdoorscollision.Main;
import bonn2.bigdoorscollision.utils.MovingDoor;
import nl.pim16aap2.bigDoors.events.DoorEventToggleStart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DoorToggleStart implements Listener {

    @EventHandler
    public void onDoorToggleStart(DoorEventToggleStart event) {
        // When door starts moving create new moving door and add to Main.movingDoors so it can be ticked
        Long id = event.getDoor().getDoorUID();
        MovingDoor movingDoor = new MovingDoor(id);
        Main.movingDoors.put(id, movingDoor);
    }
}
