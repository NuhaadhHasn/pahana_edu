<%@ include file="/WEB-INF/jspf/header.jspf" %>

<style>
    .new-card {
        background: rgba(30, 30, 45, 0.85);
        backdrop-filter: blur(15px);
        -webkit-backdrop-filter: blur(15px);
        border: 1px solid rgba(255, 255, 255, 0.1);
        border-radius: 20px;
        padding: 2.5rem;
        box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.37);
    }

    .new-card h2 {
        border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        padding-bottom: 0.5rem;
    }

    .new-card h2:first-child {
        margin-top: 0; /* Fix for the extra space */
    }

    .new-card ul {
        list-style-type: none;
        padding-left: 0;
    }

    .new-card li {
        padding-left: 1.5em;
        text-indent: -1.5em;
        margin-bottom: 0.5em;
    }

</style>

<div class="main-content">
    <div class="mb-4">
        <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-sm btn-outline-info mb-2">
            <i class="bi bi-arrow-left"></i> Back to Dashboard
        </a>
        <h1 class="mb-0">Create New Bill</h1>
    </div>

    <form id="billing-form" action="${pageContext.request.contextPath}/billing" method="post">
        <div class="row g-4">
            <%-- Left Column: Customer and Item Selection --%>
            <div class="col-lg-5">
                <div class="new-card">
                    <%-- Customer selection with toggle --%>
                    <div class="form-check form-switch mb-3">
                        <input class="form-check-input" type="checkbox" role="switch" id="walkin-toggle" name="isWalkin"
                               onchange="toggleCustomerInput()">
                        <label class="form-check-label" for="walkin-toggle">New / Walk-in Customer</label>
                    </div>

                    <div id="existing-customer-group" class="mb-3">
                        <label for="customerId" class="form-label">Select Existing Customer:</label>
                        <select class="form-select" id="customerId" name="customerId">
                            <c:forEach items="${customers}" var="customer">
                                <option value="${customer.customerId}">${customer.fullName}
                                    (${customer.accountNumber})
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div id="walkin-customer-group" style="display: none;">
                        <div class="mb-3"><label for="walkinFullName" class="form-label">Customer Full
                            Name:</label><input type="text" class="form-control" id="walkinFullName"
                                                name="walkinFullName"></div>
                        <div class="mb-3"><label for="walkinAccountNumber" class="form-label">Account
                            Number:</label><input type="text" class="form-control" id="walkinAccountNumber"
                                                  name="walkinAccountNumber"></div>
                        <div class="mb-3"><label for="walkinAddress" class="form-label">Address:</label><input
                                type="text" class="form-control" id="walkinAddress" name="walkinAddress"></div>
                        <div class="mb-3"><label for="walkinPhone" class="form-label">Phone Number:</label><input
                                type="text" class="form-control" id="walkinPhone" name="walkinPhone"></div>
                        <div class="form-check form-switch mb-3">
                            <input class="form-check-input" type="checkbox" role="switch" id="create-account-toggle"
                                   name="createAccount" onchange="toggleCreateAccount()">
                            <label class="form-check-label" for="create-account-toggle">Create Login Account</label>
                        </div>
                        <div id="create-account-fields" style="display: none;">
                            <div class="mb-3"><label for="walkinUsername" class="form-label">Username:</label><input
                                    type="text" class="form-control" id="walkinUsername" name="walkinUsername"></div>
                            <div class="mb-3"><label for="walkinPassword" class="form-label">Password:</label><input
                                    type="password" class="form-control" id="walkinPassword" name="walkinPassword">
                            </div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="item-search" class="form-label">Filter Books:</label>
                        <input type="text" class="form-control" id="item-search" placeholder="Type to filter list...">
                        <div id="item-list-container" class="list-group mt-2"
                             style="max-height: 200px; overflow-y: auto;">
                            <c:forEach items="${items}" var="item">
                                <a href="#" class="list-group-item list-group-item-action" data-item-id="${item.itemId}"
                                   data-item-name="${item.itemName}" data-item-price="${item.price}"
                                   onclick="addItemToBill(this); return false;">
                                        ${item.itemName} - <fmt:formatNumber value="${item.price}" type="currency"
                                                                             currencySymbol="Rs."/>
                                </a>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

            <%-- Right Column: Current Bill and Totals --%>
            <div class="col-lg-7">
                <div class="new-card">
                    <h3 class="card-title">Current Bill</h3>
                    <table id="bill-items-table" class="table align-middle">
                        <thead>
                        <tr>
                            <th>Item</th>
                            <th class="text-end">Qty</th>
                            <th class="text-end">Price</th>
                            <th class="text-end">Total</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody><%-- Populated by JS --%></tbody>
                    </table>
                    <hr>
                    <div class="mb-3">
                        <label for="promoId" class="form-label">Select Promotion (Optional):</label>
                        <select class="form-select" id="promoId" name="promoId" onchange="updateTotals()">
                            <option value="0">-- No Promotion --</option>
                            <c:forEach items="${promotions}" var="promo"><c:if test="${promo.active}">
                                <option value="${promo.promoId}">${promo.promoCode} (<fmt:formatNumber
                                        value="${promo.discountPercentage / 100}" type="percent"/>)
                                </option>
                            </c:if></c:forEach>
                        </select>
                    </div>
                    <div class="row g-2 align-items-center mb-3">
                        <div class="col-auto"><label for="tax-rate" class="col-form-label">Apply Tax:</label></div>
                        <div class="col"><input type="number" class="form-control" id="tax-rate" name="taxRate"
                                                value="5" step="0.1" oninput="updateTotals()"></div>
                        <div class="col-auto"><span class="form-text">%</span></div>
                    </div>
                    <div class="mb-3">
                        <label for="service-charge-input" class="form-label">Service Charge (Rs.):</label>
                        <input type="number" class="form-control" id="service-charge-input" name="serviceCharge"
                               value="0.00" step="0.01" min="0" oninput="updateTotals()">
                    </div>
                    <div class="totals-table-container" style="max-width: 400px; margin-left: auto;">
                        <table class="table table-sm">
                            <tr>
                                <td>Subtotal:</td>
                                <td class="text-end" id="subtotal">Rs. 0.00</td>
                            </tr>
                            <tr>
                                <td>Discount:</td>
                                <td class="text-end" id="discount-display">- Rs. 0.00</td>
                            </tr>
                            <tr>
                                <td>Tax:</td>
                                <td class="text-end" id="tax-amount">Rs. 0.00</td>
                            </tr>
                            <tr>
                                <td>Service Charge:</td>
                                <td class="text-end" id="service-charge-display">Rs. 0.00</td>
                            </tr>
                            <tr class="fs-5">
                                <td><strong>Grand Total:</strong></td>
                                <td class="text-end"><strong id="grand-total">Rs. 0.00</strong></td>
                            </tr>
                        </table>
                    </div>
                    <div class="d-grid mt-3">
                        <button type="submit" class="btn btn-primary btn-lg"><i class="bi bi-receipt-cutoff me-2"></i>Generate
                            Final Bill
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div id="form-submission-data" style="display: none;"></div>
    </form>
</div>

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

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
