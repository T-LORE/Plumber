package classes;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MaterialNode {
    private MaterialNode _parent;
    private ArrayList<MaterialNode> _childrens = new ArrayList<MaterialNode>();
    public enum MaterialType {
        ROOT, METAL, PLASTIC, STEEL, CARBON_STEEL, STAINLESS_STEEL, ALLOY_STEEL
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

    public MaterialNode getChildMaterial(MaterialType materialType) {
        for (MaterialNode child : _childrens) {
            if (child.getMaterialType() == materialType) {
                return child;
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
        String jsonString = "{\n" +
        "  \"материалы\": [\n" +
        "    {\n" +
        "      \"металл\": [\n" +
        "        {\n" +
        "          \"сталь\": [\n" +
        "            {\n" +
        "              \"углеродистая сталь\": {\n" +
        "                \"нержавейка\": []\n" +
        "              }\n" +
        "            },\n" +
        "            {\n" +
        "              \"легированная сталь\": []\n" +
        "            }\n" +
        "          ]\n" +
        "        }\n" +
        "      ]\n" +
        "    },\n" +
        "    {\n" +
        "      \"пластик\": []\n" +
        "    }\n" +
        "  ]\n" +
        "}";

        MaterialNode root = new MaterialNode(MaterialType.ROOT);

        // Вручную построй дерево без парсинга
        MaterialNode metal = new MaterialNode(MaterialType.METAL);
        MaterialNode plastic = new MaterialNode(MaterialType.PLASTIC);
        MaterialNode steel = new MaterialNode(MaterialType.STEEL);
        MaterialNode carbonSteel = new MaterialNode(MaterialType.CARBON_STEEL);
        MaterialNode stainlessSteel = new MaterialNode(MaterialType.STAINLESS_STEEL);
        MaterialNode alloySteel = new MaterialNode(MaterialType.ALLOY_STEEL);

        root.addChild(metal);
        root.addChild(plastic);

        metal.addChild(steel);
        steel.addChild(carbonSteel);
        carbonSteel.addChild(stainlessSteel);
        steel.addChild(alloySteel);

        return root;
    }
        
}