package pt.uc.sob.defektor.server.injectors;

import pt.uc.sob.defektor.server.model.Ijk;

public class PodDelete extends Ijk {

    public PodDelete() {
        super();
        this.setName("pod_delete");
    }

    @Override
    public Ijk name(String name) {
        return super.name(name);
    }
}
