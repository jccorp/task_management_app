import React from "react";
import AddItemForm from "./AddItemForm";
import ItemDisplay from "./ItemDisplay";

const { REACT_APP_API_ENDPOINT } = process.env;

export default function TaskListCard() {
    const [items, setItems] = React.useState(null);
  
    React.useEffect(() => {
        fetch(REACT_APP_API_ENDPOINT)
            .then(r => r.json())
            .then(setItems);
    }, []);
  
    const onNewItem = React.useCallback(
        newItem => {
            setItems([...items, newItem]);
        },
        [items],
    );
  
    const onItemUpdate = React.useCallback(
        item => {
            const index = items.findIndex(i => i.id === item.id);
            setItems([
                ...items.slice(0, index),
                item,
                ...items.slice(index + 1),
            ]);
        },
        [items],
    );
  
    const onItemRemoval = React.useCallback(
        item => {
            const index = items.findIndex(i => i.id === item.id);
            setItems([...items.slice(0, index), ...items.slice(index + 1)]);
        },
        [items],
    );
  
    if (items === null) return 'Loading...';
  
    return (
      <React.Fragment>
          <AddItemForm onNewItem={onNewItem} /> 
  
            {items.length === 0 && (
                <p className="text-center">No tasks yet! Add one above!</p>
            )}
            {items.map(item => (
                <ItemDisplay
                    item={item}
                    key={item.id}
                    onItemUpdate={onItemUpdate}
                    onItemRemoval={onItemRemoval}
                />
            ))}
      </React.Fragment>
    );
}