package model;

/**
 * Each representation of a symbol in the maps.
 * @author Richard
 */
public enum LevelItem {
    WALL('#'), EMPTY(' '),
    RTopToLeft('q'),RTopToRight('q'),RDownToLeft('q'),RDownToRight('q'),
    CTopToLeft('q'),CTopToRight('q'),CDownToLeft('q'),CDownToRight('q'),
    GTopToLeft('q'),GTopToRight('q'),GDownToLeft('q'),GDownToRight('q'),
    MTopToLeft('q'),MTopToRight('q'),MDownToLeft('q'),MDownToRight('q'),
    MHorizontal('q'),MVertical('q'),GHorizontal('q'),GVertical('q'),
    CHorizontal('q'),CVertical('q'),RHorizontal('q'),RVertical('q');
    LevelItem(char rep){ representation = rep; }
    public final char representation;
}
