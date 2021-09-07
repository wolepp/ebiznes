import { useEffect, useState } from "react";
import { getProducts, getProductsOfCategory } from "../Services/ProductAPI";
import { Col, Container, Row } from "react-bootstrap";
import ProductBox from "./ProductBox";
import { useParams } from "react-router-dom";

const Products = () => {
    const [products, setProducts] = useState([]);
    const { categoryId } = useParams();

    useEffect(() => {

        if (categoryId) {
          getProductsOfCategory(categoryId)
            .then(data => setProducts(data))
        } else {
          getProducts()
            .then(data => setProducts(data));
        }
      }, [categoryId]
    )
    ;

    return (
      <Container>
        {products.length === 0 && (
          <Row className='mt-3 mb-5'>
            <Col>
              <h2>No products available</h2>
            </Col>
          </Row>
        )}
        <Row>
          <Col>
            {products.map((p, i, { length }) => {
              if (i === length - 1) {
                return <ProductBox key={p.id} product={p} last={true} />
              } else {
                return <ProductBox key={p.id} product={p} />
              }
            })}
          </Col>
        </Row>
      </Container>
    );
  }
;

export default Products;
