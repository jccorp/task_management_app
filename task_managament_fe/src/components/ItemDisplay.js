import React from "react";
import * as ReactBootstrap from 'react-bootstrap';

const { REACT_APP_API_ENDPOINT } = process.env;

export default function ItemDisplay({ item, onItemUpdate, onItemRemoval }) {
    const { Container, Row, Col, Button } = ReactBootstrap;
  
    const toggleCompletion = () => {
        fetch(REACT_APP_API_ENDPOINT+`/${item.id}`, {
            method: 'PUT',
            body: JSON.stringify({
                description: item.description,
                completed: !item.completed,
                dueDate: item.dueDate,
            }),
            headers: { 'Content-Type': 'application/json' },
        })
            .then(r => r.json())
            .then(onItemUpdate);
    };
  
    const removeItem = () => {
        fetch(REACT_APP_API_ENDPOINT+`/${item.id}`, { method: 'DELETE' }).then(() =>
            onItemRemoval(item),
        );
    };
  
    return (
      <Container data-testid={`cont-item-${item.id}`} fluid className={`item ${item.completed && 'completed'}`}>
          <Row>             
              <Col xs={10} className="name">
                  <span className="boldDescription">{item.description}</span> <br/>
                    <i className="fa fa-calendar"></i>&nbsp;
                    {
                    new Intl.DateTimeFormat('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit' }).format(item.dueDate)
                    }
              </Col>
                
              <Col xs={1} className="text-center">
                  <Button
                      className="toggles"
                      size="sm"
                      variant="link"
                      onClick={toggleCompletion}
                      data-testid="completeButton"
                      aria-label={
                          item.completed
                              ? 'Mark task as incomplete'
                              : 'Mark task as complete'
                        }>
                        <i
                          className={`fa ${
                              item.completed ? 'fa-check-square' : 'fa-square'
                          }`}
                        />
                  </Button>
              </Col>
              <Col xs={1} className="text-center remove">
                  <Button
                      size="sm"
                      variant="link"
                      data-testid="removeButton"
                      onClick={removeItem}
                      aria-label="Remove Task">
                      <i className="fa fa-trash text-danger" />
                  </Button>
              </Col>
          </Row>
      </Container>
    );
}