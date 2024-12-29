using Newtonsoft.Json;
using RuneManager.Models;

namespace RuneManager.Data
{
    public class SWEXImportService
    {
        private readonly IDataContext _dataContext;

        public SWEXImportService(IDataContext dataContext)
        {
            _dataContext = dataContext;
        }

        public void ImportRunes(string filePath)
        {
            var jsonData = File.ReadAllText(filePath);
            var importedRunes = JsonConvert.DeserializeObject<List<SWEXRune>>(jsonData);

            if (importedRunes != null)
            {
                _dataContext.ImportRunes(importedRunes);
            }
        }
    }
}
