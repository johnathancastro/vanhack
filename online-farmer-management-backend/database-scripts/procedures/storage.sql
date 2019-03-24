drop function if exists add_storage(text, text, int, int);
create or replace function add_storage(_product_name text, _storage text, _product_amount int, _max_capacity int) returns void as $$
declare
    productKey int;
    storageKey int;
begin
    
    productKey = farmer.add_product(_product_name);

    select storage_key into storageKey
    from farmer.storage
    where upper(name) = upper(_storage);

    if found and storageKey > 0 then
        perform farmer.update_storage(storageKey, _product_name, _storage, _product_amount, _max_capacity);
    else
        insert into farmer.storage(name, max_capacity, product_key, product_amount) values (_storage, _max_capacity, productKey, _product_amount);
    end if;

end;
$$ language plpgsql;

drop function if exists update_storage(int, text, text, int, int);
create or replace function update_storage(_storage_key int, _product_name text, _storage text, _product_amount int, _max_capacity int) returns void as $$
declare
    productKey int;
begin
    productKey = farmer.add_product(_product_name);

    if exists(select 1 from farmer.storage where upper(name) = _storage and storage_key <> _storage_key) then
        raise exception 'Storage <%> already exists', _storage;
    end if;


    update farmer.storage
    set name = _storage,
    max_capacity = _max_capacity,
    product_key = productKey,
    product_amount = _product_amount
    where storage_key = _storage_key;

end;
$$ language plpgsql;


drop function if exists delete_storage(int);
create or replace function delete_storage(_storage_key int) returns void as $$
declare
    
begin
    delete from farmer.storage
    where storage_key = _storage_key;

end;
$$ language plpgsql;


drop function if exists add_product(text);
CREATE OR REPLACE FUNCTION add_product(_product_name text)
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
declare
    productKey int;
begin
    
    productKey = (select product_key from farmer.product where upper(name) = upper(_product_name) limit 1);
    if productKey > 0 then
        return productKey;
    end if;

    insert into farmer.product (name, ts) values (_product_name, now()) returning product_key into productKey;

    if productKey > 0 then
        return productKey;
    end if;
    
    return -1;

end; $function$;

