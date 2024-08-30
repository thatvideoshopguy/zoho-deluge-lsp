# `contains()`

The `contains()` function checks whether a string contains a specified substring (`searchString`). It returns `true` if the `searchString` is found within the `string`; otherwise, it returns `false`.

## Parameters

- **`string`** (Text): The main string in which to search.
- **`searchString`** (Text): The substring to search for within the `string`.

## Returns

- **`Boolean`**:
    - `true` if the `string` contains the `searchString`.
    - `false` otherwise.

## Usage

```deluge
<variable> = <string>.contains(<searchString>);
<variable> = contains(<string>, <searchString>);
```

## Example

```deluge
Product = "Zoho Creator";

check = Product.contains("Creator");    // returns true
check = Product.contains(" ");          // returns true
check = Product.contains("creator ");   // returns false

check = contains("Zoho Creator", "Creator");    // returns true
check = contains("Zoho Creator", " ");          // returns true
check = contains("Zoho Creator", "creator ");   // returns false
```
