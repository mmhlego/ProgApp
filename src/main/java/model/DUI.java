package model;

/*
    Select * [Where Condition] ?

    Delete From ... Where Condition
    Update SET ... Where Condition
    Insert Into ... Values ...
*/

public abstract class DUI {
    abstract public boolean DeleteRecord();

    abstract public boolean UpdateRecord();

    abstract public boolean InsertRecord();
}
