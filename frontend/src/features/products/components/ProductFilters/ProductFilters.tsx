import React from 'react';
import { Search } from 'react-bootstrap-icons';

interface ProductFiltersProps {
  onSearch: (query: string) => void;
  onFilterChange: (filterId: string, value: string) => void;
}

const ProductFilters: React.FC<ProductFiltersProps> = ({ onSearch, onFilterChange }) => {
  return (
    <div className="row g-3 mb-5 align-items-center">
      <div className="col-12 col-md-6 col-lg-4">
        <div className="input-group border rounded border-custom rounded-5px">
          <span className="input-group-text bg-white border-0 ps-3">
            <Search className="text-muted" size={18} />
          </span>
          <input
            type="text"
            className="form-control border-0 py-2 shadow-none rounded-5px"
            placeholder="Search products..."
            onChange={(e) => onSearch(e.target.value)}
          />
        </div>
      </div>

      <div className="col-6 col-md-3 col-lg-2">
        <select 
          className="form-select form-select-custom shadow-none"
          onChange={(e) => onFilterChange('filter1', e.target.value)}
          defaultValue=""
        >
          <option value="" disabled>Category</option>
          <option value="all">All</option>
          <option value="Running">Running</option>
          <option value="Training">Training</option>
          <option value="Lifestyle">Lifestyle</option>
          <option value="Basketball">Basketball</option>
          <option value="Hiking">Hiking</option>
          <option value="Football">Football</option>
        </select>
      </div>

      <div className="col-6 col-md-3 col-lg-2">
        <select 
          className="form-select form-select-custom shadow-none"
          onChange={(e) => onFilterChange('filter2', e.target.value)}
          defaultValue=""
        >
          <option value="" disabled>Price</option>
          <option value="low">Lowest Price</option>
          <option value="high">Highest Price</option>
        </select>
      </div>
    </div>
  );
};

export default ProductFilters;
