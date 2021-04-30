package pt.uc.sob.defektor.common.com.params;

import lombok.Data;

@Data
public class InstanceRebootParam implements ParamInterface {
    String command = "sudo reboot";
}


