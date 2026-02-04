import '../styles/CategoryNav.css';

function CategoryNav({ categories, selectedCategory, onCategorySelect }) {
  return (
    <div className="category-nav">
      <button
        className={`category-btn ${!selectedCategory ? 'active' : ''}`}
        onClick={() => onCategorySelect(null)}
      >
        전체
      </button>
      {categories.map((category) => (
        <button
          key={category.id}
          className={`category-btn ${selectedCategory === category.id ? 'active' : ''}`}
          onClick={() => onCategorySelect(category.id)}
        >
          {category.name}
        </button>
      ))}
    </div>
  );
}

export default CategoryNav;
