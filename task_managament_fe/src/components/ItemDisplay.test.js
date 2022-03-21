import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import ItemDisplay from './ItemDisplay';

function mockFetch(data){
    return jest.fn().mockImplementation(()=>
        Promise.resolve({
            ok:true,
            json: () => data
        })
    );
}

test('render task elements', () =>{

    const fakeOnItemUpdate = jest.fn();
    const fakeOnItemRemoval = jest.fn();
    const fakeTask = {id:1,description:"Task 1", completed:false,dueDate:Date.now()};

    fetch = mockFetch(JSON.stringify({id:1,description:"task 1",completed:false,dueDate:Date.now()}));

    render(<ItemDisplay item={fakeTask} key={fakeTask.id} onItemRemoval={fakeOnItemRemoval}
            onItemUpdate={fakeOnItemUpdate} />);
    const nameElement = screen.getByTestId("completeButton");
    expect(nameElement).toBeInTheDocument();

    userEvent.click(nameElement);

    expect(fetch).toHaveBeenCalledTimes(1);

});

test('remove element', () =>{

    const fakeOnItemUpdate = jest.fn();
    const fakeOnItemRemoval = jest.fn();
    const fakeTask = {id:1,description:"Task 1", completed:false,dueDate:Date.now()};

    fetch = mockFetch(JSON.stringify({}));

    render(<ItemDisplay item={fakeTask} key={fakeTask.id} onItemRemoval={fakeOnItemRemoval}
            onItemUpdate={fakeOnItemUpdate} />);

    const nameElement = screen.getByTestId("removeButton");
    expect(nameElement).toBeInTheDocument();

    userEvent.click(nameElement);

    expect(fetch).toHaveBeenCalledTimes(1);

});