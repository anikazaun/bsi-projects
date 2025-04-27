import { useEffect, useState } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [countries, setCountries] = useState([]); // all countries
  const [searchTerm, setSearchTerm] = useState(''); // search input

  useEffect(() => {
    // API call when the page loads
    axios.get('https://restcountries.com/v3.1/all')
      .then(response => {
        setCountries(response.data);
      })
      .catch(error => {
        console.error('Error fetching countries:', error);
      });
  }, []);

  // filter countries by search term
  const filteredCountries = countries.filter(country =>
    country.name.common.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="App">
      <h1>List of Countries</h1>

      {/* Search input */}
      <input
        type="text"
        placeholder="Search for a country..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        style={{ padding: '8px', marginBottom: '20px', width: '200px' }}
      />

      {/* Filtered list */}
      <ul>
        {filteredCountries.map((country, index) => (
          <li key={index}>
            {country.name.common} - Capital: {country.capital ? country.capital[0] : 'No capital'}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
