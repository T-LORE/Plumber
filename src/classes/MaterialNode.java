package classes;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MaterialNode {
    private MaterialNode _parent;
    private ArrayList<MaterialNode> _childrens = new ArrayList<MaterialNode>();
    private static MaterialNode _root = new MaterialNode(MaterialType.UNIVERSAL);

    public enum MaterialType {
        UNIVERSAL, METAL, PLASTIC, STEEL, CARBON_STEEL, STAINLESS_STEEL, ALLOY_STEEL
    }
    private MaterialType _materialType;
    public MaterialNode(MaterialType materialType) {
        _materialType = materialType;
    }

    public void setParent(MaterialNode parent) {
        _parent = parent;
    }

    public MaterialNode getParent() {
        return _parent;
    }

    public void addChild(MaterialNode child) {
        _childrens.add(child);
        child.setParent(this);
    }

    public void removeChild(MaterialNode child) {
        _childrens.remove(child);
        child.setParent(null);
    }

    public ArrayList<MaterialNode> getChildrens() {
        return _childrens;
    }

    public MaterialType getMaterialType() {
        return _materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        _materialType = materialType;
    }

    public static MaterialNode getMaterial(MaterialType materialType) {
        return _root.getChildMaterial(materialType);
    }

    private MaterialNode getChildMaterial(MaterialType materialType) {
        if (_materialType == materialType) {
            return this;
        }
        
        for (MaterialNode child : _childrens) {
            MaterialNode material = child.getChildMaterial(materialType);
            if (material != null ) {
                return  material;
            }
        }
        return null;
    }

    public boolean isThisMyAncestor(MaterialNode ancestor) {
        if (_parent == null) {
            return false;
        }
        if (_parent == ancestor) {
            return true;
        }
        return _parent.isThisMyAncestor(ancestor);
    }

    public boolean isCompatible(MaterialNode other) {

        if (_materialType == other.getMaterialType()) {
            return true;
        }

        if (isThisMyAncestor(other) || other.isThisMyAncestor(this)) {
            return true;
        }

        return false;
    }

    public static MaterialNode configure() {
        MaterialNode metal = new MaterialNode(MaterialType.METAL);
        MaterialNode plastic = new MaterialNode(MaterialType.PLASTIC);
        MaterialNode steel = new MaterialNode(MaterialType.STEEL);
        MaterialNode carbonSteel = new MaterialNode(MaterialType.CARBON_STEEL);
        DuplicatedMaterial stainlessSteel = new DuplicatedMaterial(MaterialType.STAINLESS_STEEL);
        MaterialNode alloySteel = new MaterialNode(MaterialType.ALLOY_STEEL);

        _root.addChild(metal);
        _root.addChild(plastic);

        metal.addChild(steel);
        steel.addChild(carbonSteel);
        stainlessSteel.addParent(carbonSteel);
        steel.addChild(alloySteel);
        stainlessSteel.addParent(plastic);

        return _root;
    }

    @Override
    public String toString() {
        return _materialType.toString();
    }
    
    public static MaterialNode getRoot() {
        return _root;
    }
}