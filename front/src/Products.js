const productsDb = [
  {
    id: 1,
    name: "Kawa palona",
    price: 40,
  },
  {
    id: 2,
    name: "Herbata palona",
    price: 28,
  },
  {
    id: 1,
    name: "Yerba mate",
    price: 64,
  },
];

const Products = () => {
  return (
    <div>
      <ol>
        {productsDb.map(product => (
          <li key={product.id}>{product.name}</li>
        ))}
      </ol>
    </div>
  );
};

export default Products;
