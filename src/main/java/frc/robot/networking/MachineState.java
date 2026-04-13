// a class for storing all volatile machine state data

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

public class MachineState {
    private static MachineState INSTANCE;
    public static MachineState getMachineState() {
        if (INSTANCE == null) {
            INSTANCE = new MachineState();
        }
        return INSTANCE;
    }

    private List<DataDAO<?>> dataObjects;
    private List<Sendable> dashOut;

    private MachineState() {
        dataObjects = new ArrayList<>();
        dashOut = new ArrayList<>();
    }

    public <T> void addDataDAO(DataDAO<T> d) {
        if (d == null) {
            throw new RuntimeException("DataDAO d cannot be null");
        }
        dataObjects.add(d);
        if (d.get() instanceof String) {
            dashOut.add(new Sendable() {
                @Override
                public void initSendable(SendableBuilder builder) {
                    builder.setSmartDashboardType("MachineState");
                    builder.addStringProperty(d.getTitle(), d::get, d::set);
                }
            });
        }
        if (d.get() instanceof Double) {

        }
        if (d.get() instanceof Integer) {

        }
        if (d.get() instanceof Sendable) {
            
        }
    }
}
