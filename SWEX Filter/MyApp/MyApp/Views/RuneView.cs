using RuneManager.Controllers;
using RuneManager.Data;
using RuneManager.Models;

namespace RuneManager.Views
{
    public partial class RuneView : Form
    {
        private readonly RuneController _runeController;
        private readonly IDataContext _dataContext;
        private int _currentPage = 1;
        private int _pageSize = 10;

        public RuneView(RuneController runeController, IDataContext dataContext)
        {
            _runeController = runeController;
            _dataContext = dataContext;
            InitializeComponent();
            LoadRunes();
        }

        private void LoadRunes()
        {
            var runes = _runeController.GetRunes(_currentPage, _pageSize);
            dataGridViewRunes.DataSource = new BindingSource { DataSource = runes };
        }

        private void btnImport_Click(object sender, EventArgs e)
        {
            using (OpenFileDialog openFileDialog = new OpenFileDialog())
            {
                openFileDialog.Filter = "JSON files (*.json)|*.json";
                openFileDialog.Title = "Import SWEX file";

                if (openFileDialog.ShowDialog() == DialogResult.OK)
                {
                    SWEXImportService importService = new SWEXImportService(_dataContext);
                    importService.ImportRunes(openFileDialog.FileName);
                    LoadRunes();
                }
            }
        }

        private void dataGridViewRunes_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {
            var rune = (SWEXRune)dataGridViewRunes.Rows[e.RowIndex].DataBoundItem;
            _runeController.UpdateRune(rune);
        }

        private void btnEditRune_Click(object sender, EventArgs e)
        {
            // Code pour ouvrir le formulaire d'édition des runes
        }
    }
}
