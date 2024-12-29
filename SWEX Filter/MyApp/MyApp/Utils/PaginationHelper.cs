namespace RuneManager.Utils
{
    public static class PaginationHelper<T>
    {
        public static IEnumerable<T> Paginate(IEnumerable<T> source, int pageNumber, int pageSize)
        {
            if (pageNumber < 1)
            {
                throw new ArgumentException("Page number should be greater than or equal to 1.");
            }

            if (pageSize < 1)
            {
                throw new ArgumentException("Page size should be greater than or equal to 1.");
            }

            return source.Skip((pageNumber - 1) * pageSize).Take(pageSize);
        }
    }
}
