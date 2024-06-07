package classes;

import java.util.ArrayList;

public class DuplicatedMaterial extends MaterialNode {
    private ArrayList<MaterialNode> _parents = new ArrayList<MaterialNode>();

    public DuplicatedMaterial(MaterialType materialType) {
        super(materialType);
        
    }

    @Override
    public boolean isThisMyAncestor(MaterialNode ancestor) {
        
        if (_parents.contains(ancestor)) {
            return true;
        }

        for (MaterialNode parent : _parents) {
            if (parent.isThisMyAncestor(ancestor)) {
                return true;
            }
        }

        return false;
    }

    public void addParent(MaterialNode parent) {
        _parents.add(parent);
        if (!parent.getChildrens().contains(this)) {
            parent.addChild(this);
        }
    }

    public void removeParent(MaterialNode parent) {
        _parents.remove(parent);
        if (parent.getChildrens().contains(this)) {
            parent.removeChild(this);
        }
    }
    
}
