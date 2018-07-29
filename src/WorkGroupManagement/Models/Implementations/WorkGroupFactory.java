package WorkGroupManagement.Models.Implementations;

import WorkGroupManagement.Models.Abstracts.AWorkGroup;
import WorkGroupManagement.Models.Abstracts.AWorkGroupData;
import WorkGroupManagement.Models.Abstracts.IWorkGroupFactory;

public class WorkGroupFactory implements IWorkGroupFactory {
    @Override
    public AWorkGroup getWorkGroup() {
        return new WorkGroup();
    }

    @Override
    public AWorkGroupData getWorkGroupData() {
        return new WorkGroupData();
    }

}
