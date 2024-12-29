using MyApp.Models.Enums;
using Newtonsoft.Json;
using RuneManager.Models;

namespace RuneManager.Data
{
    public class DataContext : IDataContext
    {
        private const string FiltersFilePath = "filters.json";
        private const string RunesFilePath = "runes.json";
        public IList<SWEXRune> Runes { get; set; } = new List<SWEXRune>();
        public IList<Filter> Filters { get; set; } = new List<Filter>();

        public DataContext()
        {
            // AddSampleRunes();
            // AddSampleFilters();
            LoadData();
        }

        public void AddRune(SWEXRune rune)
        {
            Runes.Add(rune);
            SaveData();
        }

        public void UpdateRune(SWEXRune rune)
        {
            var existingRune = Runes.FirstOrDefault(r => r.ID == rune.ID);
            if (existingRune != null)
            {
                existingRune.Set = rune.Set;
                existingRune.Slot = rune.Slot;
                existingRune.Rank = rune.Rank;
                existingRune.Rarity = rune.Rarity;
                existingRune.Level = rune.Level;
                existingRune.MainStat = rune.MainStat;
                existingRune.MainStatValue = rune.MainStatValue;
                existingRune.SubStat1 = rune.SubStat1;
                existingRune.SubStat1Value = rune.SubStat1Value;
                existingRune.SubStat2 = rune.SubStat2;
                existingRune.SubStat2Value = rune.SubStat2Value;
                existingRune.SubStat3 = rune.SubStat3;
                existingRune.SubStat3Value = rune.SubStat3Value;
                existingRune.SubStat4 = rune.SubStat4;
                existingRune.SubStat4Value = rune.SubStat4Value;
                existingRune.Efficiency = rune.Efficiency;
                SaveData();
            }
        }

        public void DeleteRune(int id)
        {
            var rune = Runes.FirstOrDefault(r => r.ID == id);
            if (rune != null)
            {
                Runes.Remove(rune);
                SaveData();
            }
        }

        public void ImportRunes(IEnumerable<SWEXRune> runes)
        {
            Runes.Clear();
            foreach (var rune in runes)
            {
                Runes.Add(rune);
            }
            SaveData();
        }

        public void AddFilter(Filter filter)
        {
            Filters.Add(filter);
            SaveData();
        }

        public void UpdateFilter(Filter filter)
        {
            var existingFilter = Filters.FirstOrDefault(f => f.Name == filter.Name);
            if (existingFilter != null)
            {
                existingFilter.Set = filter.Set;
                existingFilter.Slot = filter.Slot;
                existingFilter.Rarity = filter.Rarity;
                existingFilter.MinLevel = filter.MinLevel;
                existingFilter.MaxLevel = filter.MaxLevel;
                existingFilter.MainStat = filter.MainStat;
                existingFilter.MinMainStatValue = filter.MinMainStatValue;
                existingFilter.MaxMainStatValue = filter.MaxMainStatValue;
                existingFilter.SubStat1 = filter.SubStat1;
                existingFilter.MinSubStat1Value = filter.MinSubStat1Value;
                existingFilter.MaxSubStat1Value = filter.MaxSubStat1Value;
                existingFilter.SubStat2 = filter.SubStat2;
                existingFilter.MinSubStat2Value = filter.MinSubStat2Value;
                existingFilter.MaxSubStat2Value = filter.MaxSubStat2Value;
                existingFilter.SubStat3 = filter.SubStat3;
                existingFilter.MinSubStat3Value = filter.MinSubStat3Value;
                existingFilter.MaxSubStat3Value = filter.MaxSubStat3Value;
                existingFilter.SubStat4 = filter.SubStat4;
                existingFilter.MinSubStat4Value = filter.MinSubStat4Value;
                existingFilter.MaxSubStat4Value = filter.MaxSubStat4Value;
                existingFilter.IsActive = filter.IsActive;
                SaveData();
            }
        }

        public void DeleteFilter(string name)
        {
            var filter = Filters.FirstOrDefault(f => f.Name == name);
            if (filter != null)
            {
                Filters.Remove(filter);
                SaveData();
            }
        }

        private void SaveData()
        {
            File.WriteAllText(FiltersFilePath, JsonConvert.SerializeObject(Filters, Formatting.Indented));
            File.WriteAllText(RunesFilePath, JsonConvert.SerializeObject(Runes, Formatting.Indented));
        }

        private void LoadData()
        {
            if (File.Exists(FiltersFilePath))
            {
                Filters = JsonConvert.DeserializeObject<List<Filter>>(File.ReadAllText(FiltersFilePath)) ?? new List<Filter>();
            }

            if (File.Exists(RunesFilePath))
            {
                Runes = JsonConvert.DeserializeObject<List<SWEXRune>>(File.ReadAllText(RunesFilePath)) ?? new List<SWEXRune>();
            }
        }

        private void AddSampleRunes()
        {
            if (Runes.Any()) return; // Assure qu'on n'ajoute les runes exemples que si la liste est vide

            Runes.Add(new SWEXRune
            {
                ID = 1,
                Set = RuneSetType.Violent,
                Slot = RuneSlot.Slot1,
                Rank = 6,
                Rarity = RuneRarity.Legendary,
                Level = 15,
                MainStat = RuneMainStat.ATKPercentage,
                MainStatValue = 63,
                SubStat1 = RuneMainStat.HPPercentage,
                SubStat1Value = 20,
                SubStat2 = RuneMainStat.CRI_Rate,
                SubStat2Value = 15,
                SubStat3 = RuneMainStat.DEFPercentage,
                SubStat3Value = 12,
                SubStat4 = RuneMainStat.SPD,
                SubStat4Value = 8,
                Efficiency = 95
            });

            Runes.Add(new SWEXRune
            {
                ID = 2,
                Set = RuneSetType.Will,
                Slot = RuneSlot.Slot4,
                Rank = 5,
                Rarity = RuneRarity.Hero,
                Level = 12,
                MainStat = RuneMainStat.CRI_Rate,
                MainStatValue = 80,
                SubStat1 = RuneMainStat.ATKPercentage,
                SubStat1Value = 15,
                SubStat2 = RuneMainStat.HPPercentage,
                SubStat2Value = 10,
                SubStat3 = RuneMainStat.DEFPercentage,
                SubStat3Value = 9,
                SubStat4 = RuneMainStat.Accuracy,
                SubStat4Value = 10,
                Efficiency = 90
            });

            Runes.Add(new SWEXRune
            {
                ID = 3,
                Set = RuneSetType.Swift,
                Slot = RuneSlot.Slot2,
                Rank = 6,
                Rarity = RuneRarity.Legendary,
                Level = 15,
                MainStat = RuneMainStat.SPD,
                MainStatValue = 42,
                SubStat1 = RuneMainStat.ATKPercentage,
                SubStat1Value = 15,
                SubStat2 = RuneMainStat.CRI_Rate,
                SubStat2Value = 12,
                SubStat3 = RuneMainStat.HPPercentage,
                SubStat3Value = 8,
                SubStat4 = RuneMainStat.DEF,
                SubStat4Value = 10,
                Efficiency = 92
            });

            SaveData(); // Sauvegarde les runes exemples dans le fichier JSON
        }

        private void AddSampleFilters()
        {
            if (Filters.Any()) return; // Assure qu'on n'ajoute les filtres exemples que si la liste est vide

            Filters.Add(new Filter
            {
                Name = "Legendary Violent Runes",
                Set = RuneSetType.Violent,
                Rarity = RuneRarity.Legendary,
                IsActive = true
            });

            Filters.Add(new Filter
            {
                Name = "High SPD Runes",
                MainStat = RuneMainStat.SPD,
                MinMainStatValue = 40,
                IsActive = false
            });

            Filters.Add(new Filter
            {
                Name = "Swift Runes with CRI Rate",
                Set = RuneSetType.Swift,
                SubStat1 = RuneMainStat.CRI_Rate,
                MinSubStat1Value = 10,
                IsActive = true
            });

            SaveData(); // Sauvegarde les filtres exemples dans le fichier JSON
        }

    }
}
