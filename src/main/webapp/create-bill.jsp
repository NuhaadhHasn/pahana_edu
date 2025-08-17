<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Create New Bill</title>
    <style>
        body {
            font-family: sans-serif;
            margin: 2em;
        }

        .billing-container {
            display: flex;
            gap: 2em;
            align-items: flex-start;
        }

        .item-selection, .current-bill {
            flex: 1;
        }

        .form-group {
            margin-bottom: 1em;
        }

        label {
            display: block;
            margin-bottom: 0.25em;
            font-weight: bold;
        }

        select, input {
            padding: 0.5em;
            width: 100%;
            box-sizing: border-box;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1em;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 0.5em;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .totals {
            text-align: right;
            font-size: 1.1em;
            margin-top: 1em;
        }

        #item-list-container {
            border: 1px solid #ccc;
            max-height: 200px;
            overflow-y: auto;
        }

        .result-item {
            padding: 0.5em;
            cursor: pointer;
        }

        .result-item:hover {
            background-color: #f0f0f0;
        }

        .text-right {
            text-align: right;
        }

        .remove-btn {
            color: red;
            cursor: pointer;
            text-decoration: underline;
            font-size: 0.8em;
        }
    </style>
</head>

<body>
<h1>Create New Bill</h1>
<p><a href="${pageContext.request.contextPath}/dashboard.jsp">Back to Dashboard</a></p>

<form id="billing-form" action="${pageContext.request.contextPath}/billing" method="post">
    <div class="billing-container">
        <div class="item-selection">
            <%-- Customer selection with toggle --%>
            <div class="form-group">
                <input type="checkbox" id="walkin-toggle" name="isWalkin" onchange="toggleCustomerInput()">
                <label for="walkin-toggle" style="display: inline-block;">Bill to a Walk-in Customer</label>
            </div>

            <div id="existing-customer-group">
                <div class="form-group">
                    <label for="customerId">Select Existing Customer:</label>
                    <select id="customerId" name="customerId">
                        <c:forEach items="${customers}" var="customer">
                            <option value="${customer.customerId}">${customer.fullName} (${customer.accountNumber})
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div id="walkin-customer-group" style="display: none;">
                <div class="form-group">
                    <label for="walkinFullName">Customer Full Name:</label>
                    <input type="text" id="walkinFullName" name="walkinFullName">
                </div>
                <div class="form-group">
                    <label for="walkinAccountNumber">Account Number:</label>
                    <input type="text" id="walkinAccountNumber" name="walkinAccountNumber">
                </div>
                <div class="form-group">
                    <label for="walkinAddress">Address:</label>
                    <input type="text" id="walkinAddress" name="walkinAddress">
                </div>
                <div class="form-group">
                    <label for="walkinPhone">Phone Number:</label>
                    <input type="text" id="walkinPhone" name="walkinPhone">
                </div>
                <div class="form-group">
                    <input type="checkbox" id="create-account-toggle" name="createAccount"
                           onchange="toggleCreateAccount()">
                    <label for="create-account-toggle" style="display: inline-block;">Create Login Account for this
                        Customer</label>
                </div>
                <div id="create-account-fields" style="display: none;">
                    <div class="form-group">
                        <label for="walkinUsername">Username:</label>
                        <input type="text" id="walkinUsername" name="walkinUsername">
                    </div>
                    <div class="form-group">
                        <label for="walkinPassword">Password:</label>
                        <input type="password" id="walkinPassword" name="walkinPassword">
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="item-search">Filter Books:</label>
                <input type="text" id="item-search" placeholder="Type to filter the list below...">
                <div id="item-list-container">
                    <c:forEach items="${items}" var="item">
                        <div class="result-item" data-item-id="${item.itemId}" data-item-name="${item.itemName}"
                             data-item-price="${item.price}" onclick="addItemToBill(this)">
                                ${item.itemName} - Rs. ${String.format('%.2f', item.price)}
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>


        <div class="current-bill">
            <h3>Current Bill</h3>
            <table id="bill-items-table">
                <thead>
                <tr>
                    <th>Item Name</th>
                    <th class="text-right">Qty</th>
                    <th class="text-right">Price</th>
                    <th class="text-right">Total</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
            <div class="totals">
                Subtotal: <span id="subtotal">Rs. 0.00</span><br>
                Discount: - <span id="discount-display">Rs. 0.00</span><br>
                Tax: <span id="tax-amount">Rs. 0.00</span><br>
                Service Charge: <span id="service-charge-display">Rs. 0.00</span><br>
                <strong>Grand Total: <span id="grand-total">Rs. 0.00</span></strong>
            </div>

            <div class="form-group">
                <label for="promoId">Select Promotion (Optional):</label>
                <select id="promoId" name="promoId" onchange="updateTotals()">
                    <option value="0">-- No Promotion --</option>
                    <c:forEach items="${promotions}" var="promo">
                        <%-- We only show active promotions --%>
                        <c:if test="${promo.active}">
                            <option value="${promo.promoId}">${promo.promoCode} (<fmt:formatNumber
                                    value="${promo.discountPercentage / 100}" type="percent"/>)
                            </option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <%--                <input type="checkbox" id="apply-tax" name="applyTax" onchange="updateTotals()">--%>
                <label style="display: inline-block;">Apply Tax</label>
                <input type="number" id="tax-rate" name="taxRate" value="5" step="0.1"
                       style="width: 80px; display: inline-block; " oninput="updateTotals()">
                <label for="tax-rate" id="tax-rate-label" style="display: inline-block;">%</label>
            </div>

            <div class="form-group">
                <label for="service-charge-input">Service Charge (Rs.):</label>
                <input type="number" id="service-charge-input" name="serviceCharge" value="0.00" step="0.01" min="0"
                       oninput="updateTotals()">
            </div>
            <br>
            <button type="submit">Generate Final Bill</button>
        </div>
    </div>
    <div id="form-submission-data" style="display: none;"></div>
</form>
<script>
    // --- STATE & DOM REFERENCES ---
    const currentBillItems = new Map();
    const searchInput = document.getElementById('item-search');
    const itemListContainer = document.getElementById('item-list-container');
    const allItems = Array.from(itemListContainer.getElementsByClassName('result-item'));
    const billTableBody = document.querySelector('#bill-items-table tbody');
    const subtotalSpan = document.getElementById('subtotal');
    const taxAmountSpan = document.getElementById('tax-amount');
    const serviceChargeDisplaySpan = document.getElementById('service-charge-display');
    const grandTotalSpan = document.getElementById('grand-total');
    const form = document.getElementById('billing-form');
    const submissionDataContainer = document.getElementById('form-submission-data');

    // --- EVENT LISTENERS ---
    searchInput.addEventListener('input', () => {
        const searchTerm = searchInput.value.toLowerCase();
        allItems.forEach(itemDiv => {
            const itemName = itemDiv.dataset.itemName.toLowerCase();
            itemDiv.style.display = itemName.includes(searchTerm) ? 'block' : 'none';
        });
    });
    form.addEventListener('submit', prepareFormForSubmission);

    // --- FUNCTIONS ---
    function addItemToBill(itemDiv) {
        const item = {
            id: parseInt(itemDiv.dataset.itemId),
            name: itemDiv.dataset.itemName,
            price: parseFloat(itemDiv.dataset.itemPrice)
        };
        if (currentBillItems.has(item.id)) {
            currentBillItems.get(item.id).quantity++;
        } else {
            currentBillItems.set(item.id, {item: item, quantity: 1});
        }
        renderBillTable();
    }


    function toggleCreateAccount() {
        const shouldCreate = document.getElementById('create-account-toggle').checked;
        document.getElementById('create-account-fields').style.display = shouldCreate ? 'block' : 'none';
    }

    function renderBillTable() {
        billTableBody.innerHTML = '';
        if (currentBillItems.size === 0) {
            billTableBody.innerHTML = '<tr><td colspan="5" style="text-align:center;">No items added yet.</td></tr>';
        } else {
            currentBillItems.forEach((billEntry, itemId) => {
                const {item, quantity} = billEntry;
                const row = document.createElement('tr');
                const lineTotal = item.price * quantity;
                // THE FIX IS HERE: Escaping the dollar signs
                row.innerHTML = `
                    <td>\${item.name}</td>
                    <td class="text-right"><input type="number" value="\${quantity}" min="1" style="width: 60px;" onchange="updateQuantity(\${itemId}, this.value)"></td>
                    <td class="text-right">\${item.price.toFixed(2)}</td>
                    <td class="text-right">\${lineTotal.toFixed(2)}</td>
                    <td><span class="remove-btn" onclick="removeItem(\${itemId})">Remove</span></td>
                `;
                billTableBody.appendChild(row);
            });
        }
        updateTotals();
    }


    function updateQuantity(itemId, newQuantity) {
        const quantity = parseInt(newQuantity);
        if (quantity > 0) {
            currentBillItems.get(itemId).quantity = quantity;
        } else {
            currentBillItems.delete(itemId);
        }
        renderBillTable();
    }

    function removeItem(itemId) {
        currentBillItems.delete(itemId);
        renderBillTable();
    }

    function toggleCustomerInput() {
        const isWalkin = document.getElementById('walkin-toggle').checked;
        document.getElementById('existing-customer-group').style.display = isWalkin ? 'none' : 'block';
        document.getElementById('walkin-customer-group').style.display = isWalkin ? 'block' : 'none';
    }

    function updateTotals() {
        let currentSubtotal = 0;
        currentBillItems.forEach(entry => {
            currentSubtotal += entry.item.price * entry.quantity;
        });

        const promoSelect = document.getElementById('promoId');
        const selectedOption = promoSelect.options[promoSelect.selectedIndex];
        let discountAmount = 0;
        if (selectedOption.value !== "0") {
            // We can get the percentage from the text content of the option
            const promoText = selectedOption.text; // e.g., "SAVE10 (10%)"
            const percentage = parseFloat(promoText.substring(promoText.indexOf('(') + 1, promoText.indexOf('%')));
            if (!isNaN(percentage)) {
                discountAmount = currentSubtotal * (percentage / 100.0);
            }
        }

        const taxRate = parseFloat(document.getElementById('tax-rate').value) / 100.0;
        let taxAmount = !isNaN(taxRate) ? currentSubtotal * taxRate : 0;
        let serviceCharge = parseFloat(document.getElementById('service-charge-input').value) || 0;
        const grandTotal = currentSubtotal - discountAmount + taxAmount + serviceCharge;

        // THE FIX IS HERE: Escaping the dollar signs
        document.getElementById('subtotal').textContent = `Rs. \${currentSubtotal.toFixed(2)}`;
        document.getElementById('tax-amount').textContent = `Rs. \${taxAmount.toFixed(2)}`;
        document.getElementById('discount-display').textContent = `Rs. \${discountAmount.toFixed(2)}`;
        document.getElementById('service-charge-display').textContent = `Rs. \${serviceCharge.toFixed(2)}`;
        document.getElementById('grand-total').textContent = `Rs. \${grandTotal.toFixed(2)}`;
    }


    function prepareFormForSubmission(event) {
        // --- Validation (Unchanged) ---
        const customerId = document.getElementById('customerId').value;
        const walkinName = document.getElementById('walkinFullName').value;
        const isWalkinChecked = document.getElementById('walkin-toggle').checked;

        if (!isWalkinChecked && customerId === '0') { // Added a check for the default option
            alert('Please select an existing customer.');
            event.preventDefault(); // Stop the form submission
            return;
        }
        if (isWalkinChecked && walkinName.trim() === '') {
            alert('Please enter a name for the walk-in customer.');
            event.preventDefault();
            return;
        }

        console.log("--- Preparing form for submission ---");
        console.log("Number of items in bill:", currentBillItems.size);

        // --- THE FIX: Simplified and more robust data preparation ---
        submissionDataContainer.innerHTML = ''; // Clear old data

        currentBillItems.forEach((billEntry, itemId) => {
            // Create a hidden input for the item's ID
            const idInput = document.createElement('input');
            idInput.type = 'hidden';
            idInput.name = 'itemIds';
            idInput.value = itemId;
            submissionDataContainer.appendChild(idInput);

            // Create a hidden input for the item's quantity
            const quantityInput = document.createElement('input');
            quantityInput.type = 'hidden';
            quantityInput.name = 'quantities';
            quantityInput.value = billEntry.quantity;
            submissionDataContainer.appendChild(quantityInput);
        });

        console.log("Hidden inputs created:", submissionDataContainer.innerHTML);
        // This function will now run correctly and create the hidden inputs
        // before the form is submitted to the servlet.
    }

    // Initial renders
    renderBillTable();
    toggleCustomerInput();
    toggleCreateAccount();
</script>
</body>
</html>