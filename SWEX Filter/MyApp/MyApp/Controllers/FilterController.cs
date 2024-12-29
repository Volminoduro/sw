using RuneManager.Data;
using RuneManager.Models;

namespace RuneManager.Controllers
{
    public class FilterController
    {
        private readonly IDataContext _dataContext;

        public FilterController(IDataContext dataContext)
        {
            _dataContext = dataContext;
        }

        public IList<Filter> GetFilters()
        {
            return _dataContext.Filters;
        }

        public void AddFilter(Filter filter)
        {
            _dataContext.AddFilter(filter);
        }

        public void UpdateFilter(Filter filter)
        {
            _dataContext.UpdateFilter(filter);
        }

        public void DeleteFilter(string name)
        {
            _dataContext.DeleteFilter(name);
        }

        public IList<SWEXRune> ApplyFilters(IList<SWEXRune> runes)
        {
            var activeFilters = _dataContext.Filters.Where(f => f.IsActive).ToList();
            if (!activeFilters.Any())
            {
                return runes;
            }

            var filteredRunes = runes.Where(rune => activeFilters.All(filter =>
                (filter.Set == null || rune.Set == filter.Set) &&
                (filter.Slot == null || rune.Slot == filter.Slot) &&
                (filter.Rarity == null || rune.Rarity == filter.Rarity) &&
                (filter.MainStat == null || rune.MainStat == filter.MainStat) &&
                (filter.MinMainStatValue == null || rune.MainStatValue >= filter.MinMainStatValue) &&
                (filter.MaxMainStatValue == null || rune.MainStatValue <= filter.MaxMainStatValue) &&
                (filter.SubStat1 == null || rune.SubStat1 == filter.SubStat1) &&
                (filter.MinSubStat1Value == null || rune.SubStat1Value >= filter.MinSubStat1Value) &&
                (filter.MaxSubStat1Value == null || rune.SubStat1Value <= filter.MaxSubStat1Value) &&
                (filter.SubStat2 == null || rune.SubStat2 == filter.SubStat2) &&
                (filter.MinSubStat2Value == null || rune.SubStat2Value >= filter.MinSubStat2Value) &&
                (filter.MaxSubStat2Value == null || rune.SubStat2Value <= filter.MaxSubStat2Value) &&
                (filter.SubStat3 == null || rune.SubStat3 == filter.SubStat3) &&
                (filter.MinSubStat3Value == null || rune.SubStat3Value >= filter.MinSubStat3Value) &&
                (filter.MaxSubStat3Value == null || rune.SubStat3Value <= filter.MaxSubStat3Value) &&
                (filter.SubStat4 == null || rune.SubStat4 == filter.SubStat4) &&
                (filter.MinSubStat4Value == null || rune.SubStat4Value >= filter.MinSubStat4Value) &&
                (filter.MaxSubStat4Value == null || rune.SubStat4Value <= filter.MaxSubStat4Value)
            )).ToList();

            return filteredRunes;
        }
    }
}
