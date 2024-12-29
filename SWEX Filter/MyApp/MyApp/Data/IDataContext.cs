using RuneManager.Models;

namespace RuneManager.Data
{
    public interface IDataContext
    {
        IList<SWEXRune> Runes { get; set; }
        IList<Filter> Filters { get; set; }
        void AddRune(SWEXRune rune);
        void UpdateRune(SWEXRune rune);
        void DeleteRune(int id);
        void ImportRunes(IEnumerable<SWEXRune> runes);
        void AddFilter(Filter filter);
        void UpdateFilter(Filter filter);
        void DeleteFilter(string name);
    }
}
