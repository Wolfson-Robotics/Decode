package org.firstinspires.ftc.teamcode.auto.debug;

import org.firstinspires.ftc.teamcode.auto.AutoBase;
import org.firstinspires.ftc.teamcode.auto.debug.instructions.DebugInstruction;

public abstract class DebugAuto extends AutoBase {

    @Override
    public void start() {
        super.start();
        DebugInstruction[] instructions = instructions();
        for (DebugInstruction interval : instructions) {
            interval.run(this);
        }
    }

    @Override
    public void loop() {}

    public abstract DebugInstruction[] instructions();
}
