using RuneManager.Controllers;
using RuneManager.Models;

namespace RuneManager.Views
{
    public partial class FilterView : Form
    {
        private readonly FilterController _filterController;

        public FilterView(FilterController filterController)
        {
            _filterController = filterController;
            InitializeComponent();
            LoadFilters();
        }

        private void LoadFilters()
        {
            var filters = _filterController.GetFilters();
            dataGridViewFilters.DataSource = new BindingSource { DataSource = filters };
        }

        private void btnAddFilter_Click(object sender, EventArgs e)
        {
            string baseName = "Filter";
            string filterName = baseName;
            int counter = 1;

            // Ensure the filter name is unique
            while (_filterController.GetFilters().Any(f => f.Name == filterName))
            {
                filterName = $"{baseName} {counter}";
                counter++;
            }

            // Create a new filter with the unique name
            var newFilter = new Filter { Name = filterName, IsActive = false };

            // Add the new filter
            _filterController.AddFilter(newFilter);

            // Reload filters to display the new entry
            LoadFilters();
        }

        private void btnEditFilter_Click(object sender, EventArgs e)
        {
            // Code to open the filter edit form
        }

        private void btnDeleteFilter_Click(object sender, EventArgs e)
        {
            if (dataGridViewFilters.SelectedRows.Count > 0)
            {
                foreach (DataGridViewRow selectedRow in dataGridViewFilters.SelectedRows)
                {
                    var filter = (Filter)selectedRow.DataBoundItem;

                    // Delete the filter
                    _filterController.DeleteFilter(filter.Name);
                }

                // Reload filters to reflect the changes
                LoadFilters();
            }
            else
            {
                MessageBox.Show("Please select filters to delete.", "Warning", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
        }

        private void dataGridViewFilters_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {
            var filter = (Filter)dataGridViewFilters.Rows[e.RowIndex].DataBoundItem;

            // Check if the filter name already exists
            if (_filterController.GetFilters().Any(f => f.Name == filter.Name && f != filter))
            {
                MessageBox.Show("A filter with this name already exists. Please choose another name.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);

                // Restore the old filter name
                dataGridViewFilters.CancelEdit();
            }
            else
            {
                // Check for empty filter name
                if (string.IsNullOrWhiteSpace(filter.Name))
                {
                    MessageBox.Show("Filter name cannot be empty.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    dataGridViewFilters.CancelEdit();
                }
                else
                {
                    _filterController.UpdateFilter(filter);
                }
            }
        }

        private void btnActivateAllFilters_Click(object sender, EventArgs e)
        {
            var filters = _filterController.GetFilters();

            foreach (var filter in filters)
            {
                filter.IsActive = true;
                _filterController.UpdateFilter(filter);
            }

            LoadFilters();
        }

        private void btnDeactivateAllFilters_Click(object sender, EventArgs e)
        {
            var filters = _filterController.GetFilters();

            foreach (var filter in filters)
            {
                filter.IsActive = false;
                _filterController.UpdateFilter(filter);
            }

            LoadFilters();
        }
    }
}
