package bonn2.bigdoorscollision.listeners;

import bonn2.bigdoorscollision.Main;
import nl.pim16aap2.bigDoors.events.DoorEventToggleEnd;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DoorToggleEnd implements Listener {

    @EventHandler
    public void onDoorToggleEnd(DoorEventToggleEnd event) {
        // When a door stops moving kill shulkers and remove from main tick list
        Long id = event.getDoor().getDoorUID();
        if (Main.movingDoors.containsKey(id)) {
            Main.movingDoors.get(id).removeEntities();
            Main.movingDoors.remove(id);
        }
    }
}
