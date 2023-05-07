package fr.yan36.westerlife.common.objects;

import java.util.List;

public interface IDatabaseVariable {
    String tableName();
    List<String> getValues();
}
