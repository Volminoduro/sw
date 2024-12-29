using MyApp.Models;
using MyApp.Models.Enums;

namespace RuneManager.Models
{
    public class SWEXRune
    {
        public List<Filter> Filters { get; set; } = new List<Filter>();
        public int ID { get; set; }
        public RuneSetType Set { get; set; }
        public RuneSlot Slot { get; set; }
        public int Rank { get; set; }
        public RuneRarity Rarity { get; set; }
        public int Level { get; set; }
        public RuneMainStat MainStat { get; set; }
        public int MainStatValue { get; set; }
        public RuneMainStat SubStat1 { get; set; }
        public int SubStat1Value { get; set; }
        public RuneMainStat SubStat2 { get; set; }
        public int SubStat2Value { get; set; }
        public RuneMainStat SubStat3 { get; set; }
        public int SubStat3Value { get; set; }
        public RuneMainStat SubStat4 { get; set; }
        public int SubStat4Value { get; set; }
        public int Efficiency { get; set; }
        public Monster Monster { get; set; }
    }
}
