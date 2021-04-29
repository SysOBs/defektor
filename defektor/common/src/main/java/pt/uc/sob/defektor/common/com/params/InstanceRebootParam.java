package pt.uc.sob.defektor.common.com.params;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class InstanceRebootParam implements AbstractParam {
    String command = "sudo reboot";
}


