package Repository;
import Model.PrgState;

import java.util.ArrayList;
import java.util.List;


public class Repository implements IRepository {
    public final List<PrgState> programmes;
    public Repository() {
        this.programmes = new ArrayList<PrgState>();
    }
    @Override
    public PrgState getCurrentProgram(){
        return programmes.getFirst();
    }
    @Override
    public void addProgram(PrgState prgState){
        programmes.add(prgState);
    }
}
