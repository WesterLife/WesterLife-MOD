package fr.gabidut76.westerlife.common.objects;

import java.util.List;

public interface IObjectEditable {
    List<ObjectProperty> getEditableProperties();
    void onPropertyChange(ObjectProperty value);
}
