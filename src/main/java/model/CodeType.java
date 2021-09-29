package model;

public enum CodeType {
    Class, Code, Method, Enum, Interface, Dependency;

    public static CodeType FromString(String type) {
        for (CodeType ct : CodeType.values())
            if (type.equals(ct.toString()))
                return ct;
        return null;
    }
}
